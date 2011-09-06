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
package br.com.zup.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.zup.OrmCleanner;
import br.com.zup.exception.NotFoundPackage;

public class FileCleanner {

	private File javaClass;

	private static final String regexPackage = "package (.+?);";

	private static final String regexImportPackage = "(br.com.ctbc.maestro.*?.domain.+?)";

	public FileCleanner(File javaClass) throws FileNotFoundException {
		super();
		this.javaClass = javaClass;
	}

	public boolean isCleanner() throws IOException {
		BufferedReader reader = this.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			if (this.existisEntity(line) || this.existisCleanner(line)) {
				return true;
			}
		}
		return false;
	}

	private BufferedReader getReader() throws FileNotFoundException {
		FileReader entityReader = new FileReader(this.javaClass);
		BufferedReader reader = new BufferedReader(entityReader);
		return reader;
	}

	private boolean existisEntity(String line) {
		Matcher matcher = this.matcherFromRegex(line, LinesRemove.ANNOTATION_ENTITY);
		return (matcher.find());
	}

	private boolean existisCleanner(String line) {
		Matcher matcher = this.matcherFromRegex(line, LinesRemove.ANNOTATION_CLEANNER);
		return (matcher.find());
	}

	public String clean() throws IOException {
		StringBuilder entityClean = new StringBuilder();
		this.replaceContent(entityClean);
		return entityClean.toString();
	}

	private void replaceContent(StringBuilder entityClean) throws IOException {
		BufferedReader reader = this.getReader();
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			this.cleanLine(entityClean, currentLine);
		}
	}

	private void cleanLine(StringBuilder entityClean, String currentLine) {
		currentLine = this.lineRemove(currentLine);
		// currentLine = this.packageLine(currentLine); // Change package
		currentLine += System.getProperty("line.separator"); // For debug!
		entityClean.append(currentLine);
	}

	private String lineRemove(String currentLine) {
		for (LinesRemove currentRegex : LinesRemove.values()) {
			Matcher matcher = this.matcherFromRegex(currentLine, currentRegex);
			currentLine = this.lineRemoveByMatcher(currentLine, matcher);
		}
		return currentLine;
	}

	private String lineRemoveByMatcher(String currentLine, Matcher matcher) {
		while (matcher.find()) {
			currentLine = matcher.replaceAll("");
		}
		return currentLine;
	}

	private String packageLine(String line) {
		Matcher matcher = this.matcherFromRegex(line, FileCleanner.regexImportPackage);
		line = this.packageByMatcher(line, matcher);

		return line;
	}

	private String packageByMatcher(String line, Matcher matcher) {
		if (matcher.find()) {
			if (matcher.groupCount() > 0) {
				String pack = matcher.group(1);
				line = line.replaceAll(pack, String.format("%s.%s", OrmCleanner.DEFAULT_PACKAGE, pack));
			}
		}
		return line;
	}

	private Matcher matcherFromRegex(String currentLine, LinesRemove regex) {
		Pattern currentPattern = Pattern.compile(regex.lineRegex(), Pattern.DOTALL);
		Matcher matcher = currentPattern.matcher(currentLine);
		return matcher;
	}

	private Matcher matcherFromRegex(String currentLine, String regex) {
		Pattern currentPattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = currentPattern.matcher(currentLine);
		return matcher;
	}

	public String getPackageClass() throws IOException, NotFoundPackage {
		BufferedReader reader = this.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = this.matcherFromRegex(line, FileCleanner.regexPackage);
			while (matcher.find()) {
				if (matcher.groupCount() > 0) {
					return matcher.group(1);
				}
			}
		}
		throw new NotFoundPackage(String.format("%s not contains package", this.javaClass.getName()));
	}

	public String getClassName() {
		return this.javaClass.getName();
	}

	@Override
	public String toString() {
		return this.javaClass.toString();
	}
}
