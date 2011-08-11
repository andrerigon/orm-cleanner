package br.com.zup.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.zup.pomxml.Dependency;
import br.com.zup.pomxml.PomXml;

public class WriteProject {
	
	private String artifactId;
	private String artifactGroupId;
	private String version;
	private List<Dependency> dependencies;
	
	private File location;
	
	private FileWriter pom;
	private File projectDirectory;
	
	

	public WriteProject(String artifactId, String artifactGroupId, String version, List<Dependency> dependencies, File location) throws IOException {
		super();
		this.artifactId = artifactId;
		this.artifactGroupId = artifactGroupId;
		this.version = version;
		this.dependencies = dependencies;
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



	public PomXml generatePom() {
		PomXml pom = new PomXml(artifactGroupId, artifactId, version);
		pom.setDependencies(dependencies);
		return pom;
	}

}
