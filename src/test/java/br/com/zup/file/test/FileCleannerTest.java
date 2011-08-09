package br.com.zup.file.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import br.com.zup.file.FileCleanner;

public class FileCleannerTest {

	private static final String ENTITY_CLASS = "Agent.java";
	private static final String NO_ENTITY_CLASS = "AgentClean.java";

	private static final String LOCATION_CLASSES = "/target/test-classes/unit/orm-cleanner-test/src/main/java/br/com/ctbc/model/";

	private static final String localDir;

	static {
		localDir = System.getProperty("user.dir");
	}

	@Test
	public void shouldFileIsEntity() throws IOException {
		File entityFile = new File(localDir + LOCATION_CLASSES + ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(entityFile);
		assertTrue(cleanner.isEntity());
	}

	@Test
	public void shouldFileNotIsEntity() throws IOException {
		File noEntityFile = new File(localDir + LOCATION_CLASSES + NO_ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(noEntityFile);
		assertFalse(cleanner.isEntity());
	}

	@Test
	public void sholdClean() throws Exception {
		File entityFile = new File(localDir + LOCATION_CLASSES + ENTITY_CLASS);
		
		File noEntityFile = new File(localDir + LOCATION_CLASSES + NO_ENTITY_CLASS);
		FileReader noEntityReader = new FileReader(noEntityFile);
		
		FileCleanner cleanner = new FileCleanner(entityFile);
		FileReader entityReader = cleanner.clean();
		
		assertTrue( entityReader.equals(noEntityReader) );
	}

}
