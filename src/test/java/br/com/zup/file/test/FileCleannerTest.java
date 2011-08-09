package br.com.zup.file.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import br.com.zup.file.FileCleanner;

public class FileCleannerTest {

	private static final String ENTITY_CLASS = "Agent.java";
	private static final String NO_ENTITY_CLASS = "AgentClean.java";

	private static final String LOCATION_CLASSES = "/target/test-classes/unit/orm-cleanner-test/src/main/java/br/com/ctbc/model/";

	private static final String LOCAL_DIR;

	static {
		LOCAL_DIR = System.getProperty("user.dir");
	}

	@Test
	public void shouldFileIsEntity() throws IOException {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(entityFile);
		assertTrue(cleanner.isEntity());
	}

	@Test
	public void shouldFileNotIsEntity() throws IOException {
		File noEntityFile = new File(LOCAL_DIR + LOCATION_CLASSES + NO_ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(noEntityFile);
		assertFalse(cleanner.isEntity());
	}

	@Test
	public void sholdClean() throws Exception {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + ENTITY_CLASS);
		
		File noEntityFile = new File(LOCAL_DIR + LOCATION_CLASSES + NO_ENTITY_CLASS);
		FileReader noEntityReader = new FileReader(noEntityFile);
		BufferedReader noEntityBuffer = new BufferedReader(noEntityReader);
		StringBuilder noEntity = new StringBuilder();
		
		FileCleanner cleanner = new FileCleanner(entityFile);
		String entity = cleanner.clean();
		
		String line;
		while ( (line = noEntityBuffer.readLine()) != null ) {
			noEntity.append(line);
		}
		
		assertTrue( noEntity.toString().equals(entity) );
	}

}
