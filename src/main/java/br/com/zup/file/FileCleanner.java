package br.com.zup.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.zup.exception.NotFoundPackage;

public class FileCleanner {

	private File javaClass;
//	private FileReader entity;
//	private BufferedReader reader;
	
	private static final String regexPackage = "package (.*?);";
	
	public FileCleanner(File javaClass) throws FileNotFoundException {
		super();
		this.javaClass = javaClass;
//		this.entity = new FileReader(this.javaClass);
//		this.reader = new BufferedReader(this.entity);
		
	}
	
	public boolean isEntity() throws IOException {
		BufferedReader reader = getReader();
		String line;
		while ( (line = reader.readLine()) != null ) {
			if ( existisEntity(line) )
				return true;
		}
		return false;
	}

	private BufferedReader getReader() throws FileNotFoundException {
		FileReader entityReader = new FileReader(javaClass);
		BufferedReader reader = new BufferedReader(entityReader);
		return reader;
	}

	private boolean existisEntity(String line) {
		Matcher matcher = matcherFromRegex(line, LinesRemove.ANNOTATION_ENTITY);
		if (matcher.find())
			return true;
		return false;
	}

	public String clean() throws IOException {
		StringBuilder entityClean = new StringBuilder();
		replaceContent(entityClean);
		return entityClean.toString();
	}

	private void replaceContent(StringBuilder entityClean) throws IOException {
		BufferedReader reader = getReader();
		String currentLine;
		while ( (currentLine = reader.readLine()) != null ) {
			cleanLine(entityClean, currentLine);
		}
	}

	private void cleanLine(StringBuilder entityClean, String currentLine) {
		for (LinesRemove currentRegex :LinesRemove.values() ) {
			Matcher matcher = matcherFromRegex(currentLine, currentRegex);
			currentLine = cleanByMatcher(currentLine, matcher);
		}
		entityClean.append(currentLine);
	}

	private String cleanByMatcher(String currentLine, Matcher matcher) {
		if (matcher.find())
			currentLine = matcher.replaceAll("");
		return currentLine;
	}

	private Matcher matcherFromRegex(String currentLine, LinesRemove regex) {
		Pattern currentPattern = Pattern.compile(regex.lineRegex() );
		Matcher matcher = currentPattern.matcher(currentLine);
		return matcher;
	}

	private Matcher matcherFromRegex(String currentLine, String regex) {
		Pattern currentPattern = Pattern.compile(regex );
		Matcher matcher = currentPattern.matcher(currentLine);
		return matcher;
	}

	public String getPackageClass() throws IOException, NotFoundPackage {
		BufferedReader reader = getReader();
		String line;
		while ( (line = reader.readLine()) != null ) {
			Matcher matcher = matcherFromRegex(line, regexPackage);
			while (matcher.find()) {
				if (matcher.groupCount() > 0)
					return matcher.group(1);
			}
		}
		throw new NotFoundPackage(String.format("%s not contains package", javaClass.getName()));
	}
	
	public String getClassName() {
		return javaClass.getName();
	}
	
}
