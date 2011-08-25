/*
 * Copyright (c) 2011, ZUP IT INNOVATION <contato@zup.com.br> All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the ZUP IT INNOVATION nor the names of its contributors 
 * may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
