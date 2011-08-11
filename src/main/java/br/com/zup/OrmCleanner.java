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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

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
	 * @parameter expression="${project.build.directory}"
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
		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}

		File touch = new File(outputDirectory, "touch.txt");

		FileWriter writer = null;
		try {
			writer = new FileWriter(touch);

			writer.write("touch.txt");
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	public List<File> getFilesToScan() {
		File sourceLocation = new File(basedir, LOCATION_SOURCE);
		File directoryScan = new File(sourceLocation, packageToDirectory(packageScan));
		
		return Arrays.asList( directoryScan.listFiles() );
	}
	
	private static String packageToDirectory(String packageToConverter) {
		return packageToConverter.replaceAll("\\.", "/");
//		String packages[] = packageToConverter.split("\\.");
//		StringBuilder converter = unionStrings(packages);
//		return converter.toString();
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
