package br.com.zup;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import br.com.zup.exception.NotFoundPackage;
import br.com.zup.file.FileCleanner;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal touch
 * 
 * @phase process-sources
 */
public class OrmCleanner extends AbstractMojo {
	
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
		List<File> filesToScan = getFilesToScan();
		List<FileCleanner> filesToCleanAndSave = getFilesToCleanAndSave(filesToScan);
		
		cleanAndSaveFiles(filesToCleanAndSave);
	}



	private List<FileCleanner> getFilesToCleanAndSave(List<File> filesToScan) {
		List<FileCleanner> filesToCleanAndSave = new ArrayList<FileCleanner>();
		
		for (File currentFile : filesToScan) {
			try {
				FileCleanner cleanner = new FileCleanner(currentFile);
				if (cleanner.isEntity())
					filesToCleanAndSave.add(cleanner);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return filesToCleanAndSave;
	}



	private void cleanAndSaveFiles(List<FileCleanner> filesToCleanAndSave) {
		File saveSourceDirectory = new File(outputDirectory, LOCATION_SOURCE);
		for (FileCleanner currentCleanner : filesToCleanAndSave) {
			try {
				File parentSaveDirectory = new File(saveSourceDirectory, packageToDirectory(currentCleanner.getPackageClass()) );
				parentSaveDirectory.mkdirs();
				FileWriter writterClass = new FileWriter(new File(parentSaveDirectory, currentCleanner.getClassName()));
				writterClass.write(currentCleanner.clean());
				writterClass.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundPackage e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		if (directory.isDirectory())
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

}
