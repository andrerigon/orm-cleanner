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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import br.com.zup.exception.NotFoundPackage;
import br.com.zup.file.FileCleanner;

public class FileCleannerTest {

	private static final String ENTITY_CLASS = "Agent.java";
	private static final String NO_ENTITY_CLASS = "AgentClean.java";
	private static final String NO_PACKAGE_CLASS = "AgentNoPackage.java";

	private static final String LOCATION_CLASSES = "/target/test-classes/unit/orm-cleanner-test/project-test/src/main/java/br/com/ctbc/model/"
			.replaceAll("/", System.getProperty("file.separator"));

	private static final String LOCAL_DIR;

	static {
		LOCAL_DIR = System.getProperty("user.dir");
	}

	@Test
	public void shouldFileIsEntity() throws IOException {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(entityFile);
		assertTrue(cleanner.isEntity());
	}

	@Test
	public void shouldFileNotIsEntity() throws IOException {
		File noEntityFile = new File(LOCAL_DIR + LOCATION_CLASSES + NO_ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(noEntityFile);
		assertFalse(cleanner.isEntity());
	}

	@Test
	public void sholdClean() throws Exception {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + ENTITY_CLASS);

		File noEntityFile = new File(LOCAL_DIR + LOCATION_CLASSES + NO_ENTITY_CLASS);
		FileReader noEntityReader = new FileReader(noEntityFile);
		BufferedReader noEntityBuffer = new BufferedReader(noEntityReader);
		StringBuilder noEntity = new StringBuilder();

		FileCleanner cleanner = new FileCleanner(entityFile);
		String entity = cleanner.clean();

		String line;
		while ((line = noEntityBuffer.readLine()) != null) {
			noEntity.append(line);
		}

		assertTrue(noEntity.toString().equals(entity));
	}

	@Test
	public void shouldGetPackage() throws Exception {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + ENTITY_CLASS);

		FileCleanner cleanner = new FileCleanner(entityFile);
		assertEquals("br.com.ctbc.model", cleanner.getPackageClass());
	}

	@Test(expected = NotFoundPackage.class)
	public void shouldNotFoundPackageExceptionIfGetPackageFails() throws Exception {
		File entityFile = new File(LOCAL_DIR + LOCATION_CLASSES + NO_PACKAGE_CLASS);

		FileCleanner cleanner = new FileCleanner(entityFile);
		assertEquals("br.com.ctbc.maestro.vantive.domain.agent", cleanner.getPackageClass());
	}

}
