package br.com.zup.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteProject {
	
	private String artifactId;
	private File location;
	
	private FileWriter pom;
	private File projectDirectory;
	
	

	public WriteProject(String artifactId, File location) throws IOException {
		super();
		this.artifactId = artifactId;
		this.location = location;
		
		this.projectDirectory = new File(location, artifactId);
	}



	public void generateProject() throws IOException {
		writeProjectDirectory();
		writePom();
	}



	private void writePom() throws IOException {
		this.pom = new FileWriter(new File(this.projectDirectory, "pom.xml"));
		pom.write("");
		pom.close();
	}



	private void writeProjectDirectory() throws IOException {
		String error = "Directory %s, not can be write";
		if (!location.mkdirs())
			throw new IOException(String.format(error, location.toString()));
		if (!projectDirectory.mkdir())
			throw new IOException(String.format(error, projectDirectory.toString()));
	}

}
