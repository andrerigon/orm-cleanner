package br.com.zup.file.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.zup.file.WriteProject;
import br.com.zup.pomxml.Dependency;
import br.com.zup.pomxml.PomXml;

public class WriteProjectTest {

	private static final String BASE_LOCATION = "/target/export-project/";
	private static final String LOCAL_DIR;
	private static final String ARTIFACT_ID = "test-project";
	private static final String ARTIFACT_GROUP_ID = "br.com.zup.test";
	private static final String DEFAULT_OUTPUT;
	private static final PomXml POM_TEST;
	private static final File LOCATION;
	private static final String VERSION = "1.0.0-SNAPSHOT";

	private static final Dependency GUAVA = new Dependency("com.google.guava", "guava");
	private static final Dependency JUNIT = new Dependency("junit", "junit");

	static {
		LOCAL_DIR = System.getProperty("user.dir");
		DEFAULT_OUTPUT = LOCAL_DIR + BASE_LOCATION;
		LOCATION = new File(LOCAL_DIR + BASE_LOCATION);

		POM_TEST = new PomXml(ARTIFACT_GROUP_ID, ARTIFACT_ID, VERSION);
		POM_TEST.addDependency(GUAVA);
		POM_TEST.addDependency(JUNIT);
	}

	@Before
	public void deleteFiles() throws Exception {
		File output = new File(DEFAULT_OUTPUT);
		if (output.exists())
			deleteFiles(output);
	}

	public static void deleteFiles(File file) throws IOException {
		if (file.isDirectory()) {
			List<File> files = Arrays.asList(file.listFiles());
			if (!files.isEmpty())
				for (File currentFile : files) {
					deleteFiles(currentFile);
				}
		}
		file.delete();
	}

	@Test
	public void shoulGenerateProject() throws Exception {
		WriteProject writeFiles = new WriteProject(ARTIFACT_ID, ARTIFACT_GROUP_ID, VERSION, null, LOCATION);
		writeFiles.generateProject();

		File project = new File(LOCAL_DIR + BASE_LOCATION + ARTIFACT_ID);
		assertTrue(project.exists());
		assertTrue(project.isDirectory());

		File pom = new File(project, "pom.xml");
		assertTrue(pom.exists());
		assertTrue(pom.isFile());
	}

	@Test
	public void shouldPomXml() throws Exception {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		dependencies.add(GUAVA);
		dependencies.add(JUNIT);
		WriteProject writeFiles = new WriteProject(ARTIFACT_ID, ARTIFACT_GROUP_ID, VERSION, dependencies, LOCATION);
		PomXml pomXml = writeFiles.generatePom();
		assertEquals(POM_TEST, pomXml);
	}
}
