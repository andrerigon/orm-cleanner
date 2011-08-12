package br.com.zup.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {
	
	public static final String DEFAULT_OUTPUT_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/";
	
	public static final String DEFAULT_PACKAGE_SCAN = "br.com.ctbc.model";
	
	public static final String DEFAULT_DIRECTORY_SCAN = "src/main/java/br/com/ctbc/model";

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
	
	public void testGetFilesToScan() {
		List<File> scanFiles = mojo.getFilesToScan();
		assertNotNull(scanFiles);
		Collections.sort( getFilesTest() );
		Collections.sort( scanFiles );
		assertEquals(getFilesTest(), scanFiles);
	}
	
	public void testPackageToDirectory() {
		String directory;
		directory = OrmCleanner.packageToDirectory(DEFAULT_PACKAGE_SCAN);
		assertEquals("br/com/ctbc/model", directory);
	}
	
	private List<File> getFilesTest() {
		List<File> files = new ArrayList<File>();
		files.add( new File(outputDirectory, DEFAULT_DIRECTORY_SCAN + "/Agent.java") );
		files.add( new File(outputDirectory, DEFAULT_DIRECTORY_SCAN + "/AgentClean.java") );
		files.add( new File(outputDirectory, DEFAULT_DIRECTORY_SCAN + "/Agreement.java") );
		files.add( new File(outputDirectory, DEFAULT_DIRECTORY_SCAN + "/subModel/Agent.java") );
		files.add( new File(outputDirectory, DEFAULT_DIRECTORY_SCAN + "/subModel/Agreement.java") );
		
		return files;
	}

	private void setParameters() {
		mojo.setOutputDirectory(outputDirectory);
		mojo.setPackageScan(DEFAULT_PACKAGE_SCAN);
		mojo.setBasedir(outputDirectory);
	}

}
