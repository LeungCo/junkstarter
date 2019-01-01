package com.docker.junkstarter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Parser {

	public static void main(String[] args) throws IOException {
		String folderPath = "/Users/delonleung/git-clones/junkstarter/elasticsearch";

		final File folder = new File(folderPath);

		File[] myFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".json");
			}
		});

		for (File file : myFiles) {
			parse(file, folderPath);
		}
	}

	private static void parse(File currentFile, String folderPath) throws IOException {
		System.out.println(currentFile.getName());
		String fileSeparator = System.getProperty("file.separator");
		String absolutePath = folderPath + fileSeparator + "parsed-" + currentFile.getName();
		File file = new File(absolutePath);

		if (file.createNewFile()) {

			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
							
				try (BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
					String line;
					while ((line = br.readLine()) != null) {
						line = line.replace("}}", "}" + System.lineSeparator());
						line = line.replace(",\"value\":", "}" + System.lineSeparator());
						writer.write(line);
					}
					
					writer.close();
				}
			}
			
		} else {
			System.out.println("File " + absolutePath + " already exists");
		}
	}
}
