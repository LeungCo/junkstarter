package com.docker.junkstarter.util.mockaroo;

import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonArray;

public class ParseMockarooTagJsonFiles extends ParseMockarooJsonFiles {
	
	public ParseMockarooTagJsonFiles(String folderPath, String...multiColumns) {
		super(folderPath);
	}
	
	@Override
	protected void writeValueLine(String line, Writer writer) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject valueLine = (JSONObject) parser.parse(line);
		JSONObject valueJson = (JSONObject) valueLine.get("value");	
		replaceValueAsArray(valueJson, "event_ids");
		replaceValueAsArray(valueJson, "group_ids");
		writer.write(valueJson.toJSONString() + System.lineSeparator());
	}

}
