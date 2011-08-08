package br.com.zup.test;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {
	
	public static final String DEFAULT_OUTPUT_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/";

	private OrmCleanner mojo;
	
	private static File outputDirectory;
	
	static {
		outputDirectory = new File(getBasedir(), DEFAULT_OUTPUT_DIRECTORY);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		
		mojo = (OrmCleanner) lookup(OrmCleanner.ROLE);
		
		assertNotNull(mojo);
		
		setParameters();
	}
	
	public void tearDown() throws Exception {
		mojo = null;
	}
	
	public void testExecute() throws MojoExecutionException {
		mojo.execute();
		
		File touch = new File(outputDirectory, "touch.txt");
		
		if (!touch.exists())
			fail("touch not write");
	}

	public void setParameters() {
		mojo.setOutputDirectory(outputDirectory);
	}

}
