package com.docker.junkstarter.util;

import java.io.File;
import java.io.FilenameFilter;

public abstract class FolderParser {

	protected String folderPath;

	public FolderParser(String folderPath) {
		this.folderPath = folderPath;

		final File folder = new File(folderPath);

		File[] myFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return acceptFile(directory, fileName);
			}
		});

		for (File file : myFiles) {
			this.parse(file);
		}
	}

	protected abstract boolean acceptFile(File directory, String fileName);

	protected abstract void parse(File file);

}
