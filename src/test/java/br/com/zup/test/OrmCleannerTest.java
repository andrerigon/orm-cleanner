package br.com.zup.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.PlexusTestCase;

import br.com.zup.OrmCleanner;

public class OrmCleannerTest extends PlexusTestCase {

	public static final String DEFAULT_OUTPUT_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/".replaceAll("/",
			System.getProperty("file.separator"));
	public static final String DEFAULT_PACKAGE_SCAN = "br.com.ctbc.model";
	public static final String DEFAULT_DIRECTORY_SCAN = "src/main/java/br/com/ctbc/model".replaceAll("/",
			System.getProperty("file.separator"));
	private static File OUTPUT_DIRECTORY;
	private static final String DEFAULT_WRITE_DIRECTORY = "target/test-classes/unit/orm-cleanner-test/project-test/"
			.replaceAll("/", System.getProperty("file.separator"));
	private static final File EXTRACT_JAVA_DIRECTORY;

	private OrmCleanner mojo;

	static {
		OUTPUT_DIRECTORY = new File(getBasedir(), DEFAULT_OUTPUT_DIRECTORY);
		EXTRACT_JAVA_DIRECTORY = new File(new File(DEFAULT_WRITE_DIRECTORY), "src/main/java/br/com/ctbc".replaceAll(
				"/", System.getProperty("file.separator")));
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

	// public void testExecute() throws Exception {
	// mojo.execute();
	//
	// OrmCleanner mojoTest = (OrmCleanner) lookup(OrmCleanner.ROLE);
	// mojoTest.setBasedir(new File(DEFAULT_WRITE_DIRECTORY));
	// mojoTest.setPackageScan("project-test:br.com.ctbc.model;project-test:br.com.ctbc.api");
	//
	// List<File> files = new ArrayList<File>();
	// files.add( new File(EXTRACT_JAVA_DIRECTORY, "/api/Agent.java") );
	// files.add( new File(EXTRACT_JAVA_DIRECTORY, "/api/Agreement.java") );
	// files.add( new File(EXTRACT_JAVA_DIRECTORY, "/model/Agent.java") );
	// files.add( new File(EXTRACT_JAVA_DIRECTORY, "/model/Agreement.java") );
	// files.add( new File(EXTRACT_JAVA_DIRECTORY, "/model/subModel/Agent.java")
	// );
	// files.add( new File(EXTRACT_JAVA_DIRECTORY,
	// "/model/subModel/Agreement.java") );
	//
	// Collections.sort(files);
	//
	// List<File> filesScanned = mojoTest.getFilesToScan("");
	// Collections.sort(filesScanned);
	//
	// assertEquals(files,filesScanned);
	// }

	// public void testDeleteFilesOnExtract() throws Exception {
	// mojo.deleteAllFilesOnOutputDirectory(DEFAULT_PACKAGE_SCAN);
	//
	// OrmCleanner mojoTest = (OrmCleanner) lookup(OrmCleanner.ROLE);
	// mojoTest.setBasedir(new File(DEFAULT_WRITE_DIRECTORY));
	// mojoTest.setPackageScan("project-test:br.com.zup.domain;project-test:br.com.zup.api");
	//
	// List<File> files = new ArrayList<File>();
	//
	// assertEquals(files,mojoTest.getFilesToScan("project-test/src/main/java/"
	// + OrmCleanner.packageToDirectory(DEFAULT_PACKAGE_SCAN)));
	// }

	// public void testGetFilesToScan() {
	// List<File> testFiles = getFilesTest();
	// Collections.sort( testFiles );
	// System.out.println("espected: " + testFiles);
	//
	// List<File> scanFiles = mojo.getFilesToScan( "project-test/src/main/java/"
	// + OrmCleanner.packageToDirectory(DEFAULT_PACKAGE_SCAN) );
	// assertNotNull(scanFiles);
	// Collections.sort( scanFiles );
	// System.out.println("actual: " + scanFiles);
	// assertEquals(testFiles, scanFiles);
	// }

	public void testPackageToDirectory() {
		String directory;
		directory = OrmCleanner.packageToDirectory(DEFAULT_PACKAGE_SCAN);
		assertEquals("br/com/ctbc/model".replaceAll("/", System.getProperty("file.separator")), directory);
	}

	public void testGetProjectsAndPackages() {
		String packageScan = "project-test:br.com.zup.domain;project-test:br.com.comporation.api;project-exemple:br.com.organization.domain;project-exemple:br.com.organization.api";
		Map<String, List<String>> compareProjects = new HashMap<String, List<String>>();
		List<String> projectTest = new ArrayList<String>();
		projectTest.add("br.com.zup.domain");
		projectTest.add("br.com.comporation.api");
		compareProjects.put("project-test", projectTest);
		List<String> projectExemple = new ArrayList<String>();
		projectExemple.add("br.com.organization.domain");
		projectExemple.add("br.com.organization.api");
		compareProjects.put("project-exemple", projectExemple);

		Map<String, List<String>> retorno = mojo.getProjectsAndPackages(packageScan);
		assertEquals(compareProjects, retorno);
	}

	public void testGetDirsToScan() {
		String packageScan = "project-test:br.com.zup.domain;project-test:br.com.comporation.api;project-exemple:br.com.organization.domain;project-exemple:br.com.organization.api";
		Map<String, List<String>> projects = mojo.getProjectsAndPackages(packageScan);

		List<String> dirs = mojo.getDirsToScan(projects);
		List<String> compareDirs = new ArrayList<String>();
		compareDirs.add("project-test/src/main/java/br/com/zup/domain".replaceAll("/",
				System.getProperty("file.separator")));
		compareDirs.add("project-test/src/main/java/br/com/comporation/api".replaceAll("/",
				System.getProperty("file.separator")));
		compareDirs.add("project-exemple/src/main/java/br/com/organization/domain".replaceAll("/",
				System.getProperty("file.separator")));
		compareDirs.add("project-exemple/src/main/java/br/com/organization/api".replaceAll("/",
				System.getProperty("file.separator")));

		Collections.sort(dirs);
		Collections.sort(compareDirs);

		assertEquals(compareDirs, dirs);
	}

	public void testGetPackages() {
		String packageScan = "project-test:br.com.zup.domain;project-test:br.com.comporation.api;project-exemple:br.com.organization.domain;project-exemple:br.com.organization.api";
		Map<String, List<String>> projects = mojo.getProjectsAndPackages(packageScan);

		List<String> pack = mojo.getPackages(projects);
		List<String> comparePack = new ArrayList<String>();
		comparePack.add("br.com.zup.domain");
		comparePack.add("br.com.comporation.api");
		comparePack.add("br.com.organization.domain");
		comparePack.add("br.com.organization.api");
		Collections.sort(pack);
		Collections.sort(comparePack);

		assertEquals(comparePack, pack);
	}

	@SuppressWarnings("unused")
	private List<File> getFilesTest() {
		List<File> files = new ArrayList<File>();
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/Agent.java".replaceAll("/", System.getProperty("file.separator"))));
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/AgentClean.java".replaceAll("/", System.getProperty("file.separator"))));
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/Agreement.java".replaceAll("/", System.getProperty("file.separator"))));
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/subModel/Agent.java".replaceAll("/", System.getProperty("file.separator"))));
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/subModel/Agreement.java".replaceAll("/", System.getProperty("file.separator"))));
		files.add(new File(OUTPUT_DIRECTORY, "project-test/" + DEFAULT_DIRECTORY_SCAN
				+ "/AgentNoPackage.java".replaceAll("/", System.getProperty("file.separator"))));

		return files;
	}

	private void setParameters() {
		mojo.setOutputDirectory(new File(getBasedir(), DEFAULT_WRITE_DIRECTORY));
		mojo.setPackageScan("project-test:br.com.ctbc.model;project-test:br.com.ctbc.api");
		mojo.setBasedir(OUTPUT_DIRECTORY);
	}

}
