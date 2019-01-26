package com.docker.junkstarter.util.mockaroo;

import java.io.File;

import com.docker.junkstarter.util.FolderParser;

public class DeleteParsedFiles extends FolderParser {

	public DeleteParsedFiles(String folderPath) {
		super(folderPath);
	}

	@Override
	protected void parse(File file) {
		System.out.println("Deleting: " + file.getName());
		file.delete();
	}

	@Override
	protected boolean acceptFile(File directory, String fileName) {
		return fileName.startsWith(MockarooParser.PARSED_PREFIX);
	}

}
