package com.docker.junkstarter.util.mockaroo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.docker.junkstarter.util.FolderParser;
import com.google.gson.JsonArray;

public class ParseMockarooJsonFiles extends FolderParser {
	
	public ParseMockarooJsonFiles(String folderPath, String...multiColumns) {
		super(folderPath);
	}

	@Override
	protected void parse(File file) {
		System.out.println("Parsing: " + file.getName());

		String newJsonPath = folderPath + System.getProperty("file.separator") + "parsed-" + file.getName();
		File newJsonFile = new File(newJsonPath);

		try {
			if (newJsonFile.createNewFile()) {

				try (Writer writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(newJsonFile), "utf-8"))) {

					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line;

						while ((line = br.readLine()) != null) {
							writeIndexLine(line, writer);
							writeValueLine(line, writer);
						}

						writer.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("File " + newJsonPath + " already exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected boolean acceptFile(File directory, String fileName) {
		return fileName.endsWith(".json");
	}

	protected void writeIndexLine(String line, Writer writer) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonLine = (JSONObject) parser.parse(line);
		JSONObject indexJson = new JSONObject();
		JSONObject indexDefinitionJson = (JSONObject) jsonLine.get("index");
		indexJson.put("index", indexDefinitionJson);
		writer.write(indexJson.toJSONString() + System.lineSeparator());
	}

	protected void writeValueLine(String line, Writer writer) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject valueLine = (JSONObject) parser.parse(line);
		JSONObject valueJson = (JSONObject) valueLine.get("value");		
		writer.write(valueJson.toJSONString() + System.lineSeparator());
	}
	
	protected void replaceValueAsArray(JSONObject valueJson , String key) {
		String eventId = (String) valueJson.get(key);
		valueJson.remove(key);
		JsonArray eventArray = new JsonArray();
		eventArray.add(eventId);
		valueJson.put(key, eventArray);
	}

}
