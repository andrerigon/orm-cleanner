package br.com.zup.pomxml;

import java.util.ArrayList;
import java.util.List;


public class PomXml {

	private final String modelVersion = "4.0.0";
	
	private String groupId;
	private String artifactId;
	private String version;
	private List<Dependency> dependencies;
	
	
	
	private PomXml(String groupId, String artifactId, String version, List<Dependency> dependencies) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.dependencies = dependencies;
	}

	public PomXml(String groupId, String artifactId, String version) {
		this(groupId, artifactId, version, new ArrayList<Dependency>() );
	}
	
	public void addDependency(Dependency dependency) {
		this.dependencies.add(dependency);
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public String getModelVersion() {
		return modelVersion;
	}
}