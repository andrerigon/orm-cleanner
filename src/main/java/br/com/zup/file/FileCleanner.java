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
	
	public FileCleanner(File javaClass) {
		super();
		this.javaClass = javaClass;
	}

	public boolean isEntity() throws IOException {
		FileReader entity = new FileReader(javaClass);
		BufferedReader reader = new BufferedReader(entity);
		
		String line;
		Pattern regex = Pattern.compile(LinesRemove.ANNOTATION_ENTITY.lineRegex());  
		while ( (line = reader.readLine()) != null ) {
			Matcher matcher = regex.matcher(line);
			if (matcher.find())
				return true;
		}
		return false;
	}

	public FileReader clean() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
