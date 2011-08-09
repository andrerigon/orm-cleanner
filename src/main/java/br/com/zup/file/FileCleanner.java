package br.com.zup.file;

import java.io.File;
import java.io.FileReader;

public class FileCleanner {

	File javaClass;
	
	public FileCleanner(File javaClass) {
		super();
		this.javaClass = javaClass;
	}

	public boolean isEntity() {
		// TODO: verificar se tem annotation @Entity
		return false;
	}
	
	
}
