package br.com.zup.file.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import br.com.zup.file.JavaCodeFormatter;

public class FileFormatterTest {

	private static final String UNFORMATTED_CODE = "public enum X {A,	B,	C,	D,	E,	F}";
	
	private final String FORMATTED_CODE = readFile("formatted_enum.txt");

	@Test
	public void should_format_code() {

		assertEquals(FORMATTED_CODE, new JavaCodeFormatter().format(UNFORMATTED_CODE));
	}

	private String readFile(String code) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(code);
		return new Scanner(is).useDelimiter("\\Z").next();
	}
}
