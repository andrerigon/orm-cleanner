package br.com.zup.test;

import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {
	
	public static final String DEFAULT_POM_TEST = "target/test-classes/unit/orm-cleanner-test/plugin-config.xml";

	private OrmCleanner mojo;
	
	public void setUp() throws Exception {
		super.setUp();
		
		mojo = (OrmCleanner) lookup(OrmCleanner.ROLE);
		
		assertNotNull(mojo);
	}

	public void setParameters() {
		
	}

}
