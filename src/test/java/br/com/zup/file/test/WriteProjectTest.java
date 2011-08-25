package br.com.zup.file.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.zup.file.WriteProject;

public class WriteProjectTest {

	private static final String BASE_LOCATION = "/target/export-project/".replaceAll("/",
			System.getProperty("file.separator"));
	private static final String LOCAL_DIR;
	private static final String ARTIFACT_ID = "test-project";
	private static final String DEFAULT_OUTPUT;
	private static final File LOCATION;

	static {
		LOCAL_DIR = System.getProperty("user.dir");
		DEFAULT_OUTPUT = LOCAL_DIR + BASE_LOCATION;
		LOCATION = new File(LOCAL_DIR + BASE_LOCATION);
	}

	@Before
	public void deleteFiles() throws Exception {
		File output = new File(DEFAULT_OUTPUT);
		if (output.exists())
			deleteFiles(output);
	}

	public static void deleteFiles(File file) throws IOException {
		if (file.isDirectory()) {
			List<File> files = Arrays.asList(file.listFiles());
			if (!files.isEmpty())
				for (File currentFile : files) {
					deleteFiles(currentFile);
				}
		}
		file.delete();
	}

	@Test
	public void shouldWriteDirectoryIfNotExists() throws Exception {
		File projectDirectory = new File(LOCATION, ARTIFACT_ID);
		String pkg = "br/com/zup/test".replaceAll("/", System.getProperty("file.separator"));
		WriteProject writter = new WriteProject(projectDirectory);
		writter.writeDirectoryIfNotExists(pkg);

		File directory = new File(projectDirectory, "src/main/java/".replaceAll("/",
				System.getProperty("file.separator"))
				+ pkg);
		assertTrue(directory.exists());
	}

	@Test(expected = IOException.class)
	public void shouldIoExceptionIfWriteDirectoryIfNotExistsFail() throws Exception {
		File projectDirectory = new File(LOCATION, ARTIFACT_ID);
		String pkg = "br/com/zup/test".replaceAll("/", System.getProperty("file.separator"));
		String src = "src/main/java/".replaceAll("/", System.getProperty("file.separator"));
		WriteProject writter = new WriteProject(projectDirectory);

		File directory = new File(projectDirectory, src + pkg);
		directory.getParentFile().mkdirs();
		directory.createNewFile();

		writter.writeDirectoryIfNotExists(pkg);
	}

	@Test
	public void shouldWriteDirectoryIfExists() throws Exception {
		File projectDirectory = new File(LOCATION, ARTIFACT_ID);
		String pkg = "br/com/zup/test".replaceAll("/", System.getProperty("file.separator"));
		String src = "src/main/java/".replaceAll("/", System.getProperty("file.separator"));
		WriteProject writter = new WriteProject(projectDirectory);

		File directory = new File(projectDirectory, src + pkg);
		directory.mkdirs();

		writter.writeDirectoryIfNotExists(pkg);

		assertTrue(directory.exists());
	}
}
