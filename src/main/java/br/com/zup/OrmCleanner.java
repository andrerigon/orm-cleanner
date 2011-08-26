/*
 * Copyright (c) 2011, ZUP IT INNOVATION <contato@zup.com.br> All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the ZUP IT INNOVATION nor the names of its contributors 
 * may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.zup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import br.com.zup.exception.NotFoundPackage;
import br.com.zup.file.FileCleanner;

/**
 * Create classes clean.
 * 
 * @author <a href="mailto:abnercarleto@ymail.com">Abner Carleto</a>
 * @version $Id: 1.0.0 $
 * 
 * @goal compile
 * @phase compile
 * 
 */
public class OrmCleanner extends AbstractMojo {

	private static final String EXCLUDE_SVN = ".svn";

	private static final String fileSeparator = System.getProperty("file.separator");

	private static final String LOCATION_SOURCE = "src/main/java".replaceAll("/", fileSeparator);
	
	private static final String DEFAULT_PACKAGE = "cleanner";

	/**
	 * Location of the output jar.
	 * 
	 * @parameter expression="${save.project}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Location of the package to scan.
	 * 
	 * @parameter expression="${package.scan}"
	 * @required
	 */
	private String packageScan;

	/**
	 * Root directory of the project.
	 * 
	 * @parameter expression="${basedir}"
	 * @required
	 */
	private File basedir;

	public void execute() throws MojoExecutionException {
		this.getLog().debug("In OrmCleanner::execute()");

		// Project and list of packages
		Map<String, List<String>> projectsToScan = this.getProjectsAndPackages(packageScan);

		List<String> scanDirs = this.getDirsToScan(projectsToScan);

		List<String> packages = this.getPackages(projectsToScan);
		for (String pack : packages) {
			this.deleteAllFilesOnOutputDirectory(pack);
		}

		List<File> filesToScan = new ArrayList<File>();
		for (String dir : scanDirs) {
			filesToScan.addAll(this.getFilesToScan(dir));
		}

		List<FileCleanner> filesToCleanAndSave = this.getFilesToCleanAndSave(filesToScan);

		this.cleanAndSaveFiles(filesToCleanAndSave);
	}

	public Map<String, List<String>> getProjectsAndPackages(String projectsAndPackages) {
		this.getLog().debug("In OrmCleanner::setBasedir()");

		Map<String, List<String>> projectsToScan = new HashMap<String, List<String>>();

		String[] packagesAndProjects = projectsAndPackages.split(";");
		for (String projectAndPackage : packagesAndProjects) {
			String[] scan = projectAndPackage.split(":");
			if (projectsToScan.containsKey(scan[0].trim())) {
				projectsToScan.get(scan[0].trim()).add(scan[1].trim());
			} else {
				List<String> packages = new ArrayList<String>();
				packages.add(scan[1].trim());
				projectsToScan.put(scan[0].trim(), packages);
			}
		}
		return projectsToScan;
	}

	public List<String> getPackages(Map<String, List<String>> projects) {
		this.getLog().debug("In OrmCleanner::getPackages()");

		List<String> packages = new ArrayList<String>();
		for (String key : projects.keySet()) {
			for (String currentPackage : projects.get(key)) {
				packages.add(currentPackage);
			}
		}
		return packages;
	}

	public List<String> getDirsToScan(Map<String, List<String>> projects) {
		this.getLog().debug("In OrmCleanner::getDirsToScan()");

		List<String> directories = new ArrayList<String>();
		for (String key : projects.keySet()) {
			for (String currentPackage : projects.get(key)) {
				directories.add(key + fileSeparator + LOCATION_SOURCE + fileSeparator
						+ OrmCleanner.packageToDirectory(currentPackage));
			}
		}
		return directories;
	}

	private List<FileCleanner> getFilesToCleanAndSave(List<File> filesToScan) {
		this.getLog().debug("In OrmCleanner::getFilesToCleanAndSave()");

		List<FileCleanner> filesToCleanAndSave = new ArrayList<FileCleanner>();

		this.getLog().info(String.format("Scanning package %s", packageScan));
		for (File currentFile : filesToScan) {
			try {
				FileCleanner cleanner = new FileCleanner(currentFile);
				this.getLog().debug(String.format("File: %s", currentFile));
				if (cleanner.isCleanner()) {
					filesToCleanAndSave.add(cleanner);
					this.getLog().debug("is entity: %s");
				}
			} catch (FileNotFoundException e) {
				this.getLog().error(String.format("File %s not found", currentFile.toString()), e);
			} catch (IOException e) {
				this.getLog().error(String.format("Errors occurred when reading file: %s", currentFile.toString()), e);
			}
		}
		return filesToCleanAndSave;
	}

	private void cleanAndSaveFiles(List<FileCleanner> filesToCleanAndSave) {
		this.getLog().debug("In OrmCleanner::cleanAndSaveFiles()");

		this.getLog().info(String.format("Saving files: %s", filesToCleanAndSave.toString()));
		File outputDirectory = new File(this.outputDirectory, OrmCleanner.LOCATION_SOURCE);
		File saveSourceDirectory = new File(outputDirectory, OrmCleanner.DEFAULT_PACKAGE);
		for (FileCleanner currentCleanner : filesToCleanAndSave) {
			try {
				File parentSaveDirectory = new File(saveSourceDirectory, OrmCleanner.packageToDirectory(currentCleanner
						.getPackageClass()));
				parentSaveDirectory.mkdirs();
				
				File fileWrite = new File(parentSaveDirectory, currentCleanner.getClassName());
				FileWriter writterClass = new FileWriter(fileWrite);
				
				writterClass.write(currentCleanner.clean());
				writterClass.close();
				
				this.getLog().info(String.format("Saving %s", fileWrite.toString()));
			} catch (IOException e) {
				this.getLog().error(String.format("Errors occurred when saving file: %s", currentCleanner.toString()),
						e);
			} catch (NotFoundPackage e) {
				this.getLog().error(String.format("File %s, not contains package", currentCleanner.toString()), e);
			}
		}
	}

	public List<File> getFilesToScan(String baseDirectory) {
		this.getLog().debug("In OrmCleanner::getFilesToScan()");

		File sourceLocation = new File(this.basedir, baseDirectory);
		List<File> files = new ArrayList<File>();

		this.getLog().info(String.format("Dirs: %s", baseDirectory));
		files.addAll(this.getAllFilesFromDirectory(sourceLocation));

		return files;
	}

	private List<File> getAllFilesFromDirectory(File directory) {
		this.getLog().debug("In OrmCleanner::getAllFilesFromDirectory()");

		List<File> files = new ArrayList<File>();
		if (directory.isDirectory() && !directory.getName().equals(EXCLUDE_SVN))
			for (File currentFile : directory.listFiles())
				files.addAll(this.getAllFilesFromDirectory(currentFile));
		return directory.isFile() ? this.addFileToListt(directory, files) : files;
	}

	private List<File> addFileToListt(File directory, List<File> list) {
		this.getLog().debug("In OrmCleanner::addFileToListt()");

		list.add(directory);
		return list;
	}

	public static String packageToDirectory(String packageToConverter) {
		return packageToConverter.replaceAll("\\.", OrmCleanner.fileSeparator);
	}

	public void setOutputDirectory(File outputDirectory) {
		this.getLog().debug("In OrmCleanner::setOutputDirectory()");

		this.outputDirectory = outputDirectory;
	}

	public void setPackageScan(String packageScan) {
		this.getLog().debug("In OrmCleanner::setPackageScan()");

		this.packageScan = packageScan;
	}

	public void setBasedir(File basedir) {
		this.getLog().debug("In OrmCleanner::setBasedir()");

		this.basedir = basedir;
	}

	public void deleteAllFilesOnOutputDirectory(String packageToDelete) {
		this.getLog().debug("In OrmCleanner::deleteAllFilesOnOutputDirectory()");

		File directoryToDelete = new File(new File(this.outputDirectory, OrmCleanner.LOCATION_SOURCE),
				OrmCleanner.packageToDirectory(packageToDelete));
		try {
			this.deleteFiles(directoryToDelete);
		} catch (IOException e) {
			this.getLog().warn("Error deleting old files", e);
		}
	}

	public void deleteFiles(File file) throws IOException {
		this.getLog().debug("In OrmCleanner::deleteFiles()");

		if (file.isDirectory()) {
			List<File> files = Arrays.asList(file.listFiles());
			if (!files.isEmpty())
				for (File currentFile : files) {
					this.deleteFiles(currentFile);
				}
		}
		file.delete();
	}

}
