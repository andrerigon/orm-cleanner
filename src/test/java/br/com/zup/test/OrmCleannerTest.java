package br.com.zup.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {
	
	public static final String DEFAULT_OUTPUT_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/";
	public static final String DEFAULT_PACKAGE_SCAN = "br.com.ctbc.model";
	public static final String DEFAULT_DIRECTORY_SCAN = "src/main/java/br/com/ctbc/model";
	private static File OUTPUT_DIRECTORY;
	private static final String DEFAULT_WRITE_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/project-test/";
	private static final File EXTRACT_JAVA_DIRECTORY;
	
	private OrmCleanner mojo;
	
	static {
		OUTPUT_DIRECTORY = new File(getBasedir(), DEFAULT_OUTPUT_DIRECTORY);
		EXTRACT_JAVA_DIRECTORY = new File(new File(DEFAULT_WRITE_DIRECTORY), DEFAULT_DIRECTORY_SCAN );
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
	
	public void testExecute() throws Exception {
		mojo.execute();
		
		OrmCleanner mojoTest = (OrmCleanner) lookup(OrmCleanner.ROLE);
		mojoTest.setBasedir(new File(DEFAULT_WRITE_DIRECTORY));
		mojoTest.setPackageScan(DEFAULT_PACKAGE_SCAN);
		
		List<File> files = new ArrayList<File>();
		files.add( new File(EXTRACT_JAVA_DIRECTORY, "/Agent.java") );
		files.add( new File(EXTRACT_JAVA_DIRECTORY, "/Agreement.java") );
		files.add( new File(EXTRACT_JAVA_DIRECTORY, "/subModel/Agent.java") );
		files.add( new File(EXTRACT_JAVA_DIRECTORY, "/subModel/Agreement.java") );
		
		Collections.sort(files);
		
		List<File> filesScanned = mojoTest.getFilesToScan();
		Collections.sort(filesScanned);
		
		assertEquals(files,filesScanned);
	}
	
	public void testDeleteFilesOnExtract() throws Exception {
		mojo.deleteAllFilesOnOutputDirectory();
		
		OrmCleanner mojoTest = (OrmCleanner) lookup(OrmCleanner.ROLE);
		mojoTest.setBasedir(new File(DEFAULT_WRITE_DIRECTORY));
		mojoTest.setPackageScan(DEFAULT_PACKAGE_SCAN);
		
		List<File> files = new ArrayList<File>();
		
		assertEquals(files,mojoTest.getFilesToScan());
	}
	
	public void testGetFilesToScan() {
		List<File> scanFiles = mojo.getFilesToScan();
		assertNotNull(scanFiles);
		List<File> testFiles = getFilesTest();
		Collections.sort( testFiles );
		Collections.sort( scanFiles );
		assertEquals(testFiles, scanFiles);
	}
	
	public void testPackageToDirectory() {
		String directory;
		directory = OrmCleanner.packageToDirectory(DEFAULT_PACKAGE_SCAN);
		assertEquals("br/com/ctbc/model", directory);
	}
	
	private List<File> getFilesTest() {
		List<File> files = new ArrayList<File>();
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/Agent.java") );
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/AgentClean.java") );
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/Agreement.java") );
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/subModel/Agent.java") );
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/subModel/Agreement.java") );
		files.add( new File(OUTPUT_DIRECTORY, DEFAULT_DIRECTORY_SCAN + "/AgentNoPackage.java") );
		
		return files;
	}

	private void setParameters() {
		mojo.setOutputDirectory(new File(getBasedir(), DEFAULT_WRITE_DIRECTORY));
		mojo.setPackageScan(DEFAULT_PACKAGE_SCAN);
		mojo.setBasedir(OUTPUT_DIRECTORY);
	}
	
}
