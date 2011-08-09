package br.com.zup.file.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import br.com.zup.file.FileCleanner;


public class FileCleannerTest {

	private static final String ENTITY_CLASS = "/target/test-classes/unit/orm-cleanner-test/src/main/java/br/com/ctbc/model/Agent.java";
	
	private static final String localDir;
	
	static {
		localDir = System.getProperty("user.dir");
	}
	
	@Test
	public void shouldFileIsEntity() {
		File entityFile = new File(localDir + ENTITY_CLASS);
		
		FileCleanner cleanner = new FileCleanner(entityFile);
		assertTrue( cleanner.isEntity() );
	}
}
