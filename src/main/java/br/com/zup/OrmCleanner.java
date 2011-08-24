package br.com.zup;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private static final String LOCATION_SOURCE = "src/main/java";
	
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
		deleteAllFilesOnOutputDirectory();
		
		List<File> filesToScan = getFilesToScan();
		List<FileCleanner> filesToCleanAndSave = getFilesToCleanAndSave(filesToScan);
		
		cleanAndSaveFiles(filesToCleanAndSave);
	}



	private List<FileCleanner> getFilesToCleanAndSave(List<File> filesToScan) {
		List<FileCleanner> filesToCleanAndSave = new ArrayList<FileCleanner>();
		
		getLog().info(String.format("Scanning package %s", packageScan));
		for (File currentFile : filesToScan) {
			try {
				FileCleanner cleanner = new FileCleanner(currentFile);
				if (cleanner.isEntity())
					filesToCleanAndSave.add(cleanner);
			} catch (FileNotFoundException e) {
				getLog().error(String.format("File %s not found", currentFile.toString()), e);
			} catch (IOException e) {
				 getLog().error(String.format("Errors occurred when reading file: %s", currentFile.toString()), e);
			}
		}
		return filesToCleanAndSave;
	}



	private void cleanAndSaveFiles(List<FileCleanner> filesToCleanAndSave) {
		getLog().info("Saving files:");
		File saveSourceDirectory = new File(outputDirectory, LOCATION_SOURCE);
		for (FileCleanner currentCleanner : filesToCleanAndSave) {
			try {
				File parentSaveDirectory = new File(saveSourceDirectory, packageToDirectory(currentCleanner.getPackageClass()) );
				parentSaveDirectory.mkdirs();
				File fileWrite = new File(parentSaveDirectory, currentCleanner.getClassName());
				FileWriter writterClass = new FileWriter(fileWrite);
				writterClass.write(currentCleanner.clean());
				writterClass.close();
				getLog().info(String.format("Saving %s", fileWrite.toString()));
			} catch (IOException e) {
				getLog().error(String.format("Errors occurred when saving file: %s", currentCleanner.toString()), e);
			} catch (NotFoundPackage e) {
				getLog().error(String.format("File %s, not contains package", currentCleanner.toString()), e);
			} 
		}
	}
	
	
	
	public List<File> getFilesToScan() {
		File sourceLocation = new File(basedir, LOCATION_SOURCE);
		File directoryScan = new File(sourceLocation, packageToDirectory(packageScan));
		
		return getAllFilesFromDirectory(directoryScan);
	}
	
	private List<File> getAllFilesFromDirectory(File directory) {
		List<File> files = new ArrayList<File>();
		if (directory.isDirectory() && !directory.getName().equals(EXCLUDE_SVN) )
			for (File currentFile : directory.listFiles())
				files.addAll( getAllFilesFromDirectory(currentFile) );
		return directory.isFile() ? addFileToListt(directory, files) : files;
	}

	private List<File> addFileToListt(File directory, List<File> list) {
		list.add(directory);
		return list;
	}
	
	public static String packageToDirectory(String packageToConverter) {
		return packageToConverter.replaceAll("\\.", "/");
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void setPackageScan(String packageScan) {
		this.packageScan = packageScan;
	}

	public void setBasedir(File basedir) {
		this.basedir = basedir;
	}



	public void deleteAllFilesOnOutputDirectory() {
		File directoryToDelete = new File( new File(outputDirectory, LOCATION_SOURCE), packageToDirectory(packageScan) );
		try {
			deleteFiles(directoryToDelete);
		} catch (IOException e) {
			getLog().warn("Error deleting old files", e);
		}
	}
	
	public void deleteFiles(File file) throws IOException {
		if (file.isDirectory()) {
			List<File> files = Arrays.asList(file.listFiles());
			if (!files.isEmpty())
				for (File currentFile : files) {
					deleteFiles(currentFile);
				}
		}
		file.delete();
	}

}
