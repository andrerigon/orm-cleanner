package br.com.zup.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileCleanner {

	File javaClass;
	FileReader entity;
	BufferedReader reader;
	
	public FileCleanner(File javaClass) throws FileNotFoundException {
		super();
		this.javaClass = javaClass;
		this.entity = new FileReader(this.javaClass);
		this.reader = new BufferedReader(this.entity);
	}

	public boolean isEntity() throws IOException {
		String line;
		Pattern regex = Pattern.compile(LinesRemove.ANNOTATION_ENTITY.lineRegex());  
		while ( (line = reader.readLine()) != null ) {
			Matcher matcher = regex.matcher(line);
			if (matcher.find())
				return true;
		}
		return false;
	}

	public String clean() throws IOException {
		StringBuilder entityClean = new StringBuilder();
		replaceContent(entityClean);
		return entityClean.toString();
	}

	private void replaceContent(StringBuilder entityClean) throws IOException {
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

	private Matcher matcherFromRegex(String currentLine, LinesRemove currentRegex) {
		Pattern currentPattern = Pattern.compile(currentRegex.lineRegex() );
		Matcher matcher = currentPattern.matcher(currentLine);
		return matcher;
	}
	
}
