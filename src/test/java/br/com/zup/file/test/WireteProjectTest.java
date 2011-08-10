package br.com.zup.file.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.zup.file.WriteProject;

public class WireteProjectTest {

	private static final String BASE_LOCATION = "/target/export-project/";
	
	private static final String LOCAL_DIR;
	
	private static final String ARTIFACT_ID = "test-project";
	
	private static final String DEFAULT_OUTPUT;

	static {
		LOCAL_DIR = System.getProperty("user.dir");
		DEFAULT_OUTPUT = LOCAL_DIR + BASE_LOCATION;
	}
	
	@Before
	public void deleteFiles() throws Exception {
		File output = new File(DEFAULT_OUTPUT);
		if (output.exists())
			deleteFiles(output);
	}
	
	private void deleteFiles(File file) throws IOException {
		if (file.isDirectory()) {
			List<File> files = Arrays.asList( file.listFiles() );
			if (!files.isEmpty())
				for (File currentFile : files) {
					deleteFiles(currentFile);
				}
		}
		file.delete();
	}
	
	@Test
	public void shoulGenerateProject() throws Exception { 
		File location = new File(LOCAL_DIR + BASE_LOCATION);
		WriteProject writeFiles = new WriteProject(ARTIFACT_ID, location);
		writeFiles.generateProject();
		
		File project = new File(LOCAL_DIR + BASE_LOCATION + ARTIFACT_ID);
		assertTrue( project.exists() );
		assertTrue( project.isDirectory() );
		
		File pom = new File(project, "pom.xml");
		assertTrue( pom.exists() );
		assertTrue( pom.isFile() );
	}
}
