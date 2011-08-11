package br.com.zup.file;

import java.io.File;
import java.io.IOException;

public class WriteProject {
	
	private File projectLocation;
	private static final String DEFAULT_SOURCE_LOCATION = "src/main/java/"; 
	
	public WriteProject(File projectLocation) throws IOException {
		super();
		this.projectLocation = projectLocation;
	}

	public void writeDirectoryIfNotExists(String directory) throws IOException {
		File directoryFile = new File(projectLocation, DEFAULT_SOURCE_LOCATION + directory);
		if (!directoryFile.exists())
			directoryFile.mkdirs();
		else if (directoryFile.isFile())
			throw new IOException("Directory can not be created because it already exists a file with the same name");
	}

}
