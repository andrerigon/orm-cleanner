package br.com.zup.test;

import java.io.File;

import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {
	
	public static final String DEFAULT_OUTPUT_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/";

	private OrmCleanner mojo;
	
	public void setUp() throws Exception {
		super.setUp();
		
		mojo = (OrmCleanner) lookup(OrmCleanner.ROLE);
		
		assertNotNull(mojo);
	}

	public void setParameters() {
		mojo.setOutputDirectory(new File(getBasedir(), DEFAULT_OUTPUT_DIRECTORY));
	}

}
