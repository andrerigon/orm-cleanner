package br.com.zup.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.zup.pomxml.Dependency;

public class WriteProject {
	
	private File location;
	
	public WriteProject(File location) throws IOException {
		super();
		this.location = location;
	}

	public void generateProject() throws IOException {
		writeProjectDirectory();
	}

	private void writeProjectDirectory() throws IOException {
		String error = "Directory %s, not can be write";
		if (!location.mkdirs())
			throw new IOException(String.format(error, location.toString()));
		if (!projectDirectory.mkdir())
			throw new IOException(String.format(error, projectDirectory.toString()));
	}

}
