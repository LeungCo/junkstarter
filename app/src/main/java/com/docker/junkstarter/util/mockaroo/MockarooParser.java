package com.docker.junkstarter.util.mockaroo;

import java.io.IOException;

public class MockarooParser {

	public static final String PARSED_PREFIX = "parsed-";

	public static void main(String[] args) throws IOException {
		parseEvents();
		parseTags();
	}
	
	private static void parseEvents() {
		String eventsFolder = "/Users/delonleung/git-clones/junkstarter/elasticsearch/events";
		new DeleteParsedFiles(eventsFolder);
		new ParseMockarooJsonFiles(eventsFolder);
	}
	
	private static void parseTags() {
		String tagsFolder = "/Users/delonleung/git-clones/junkstarter/elasticsearch/tags";
		new DeleteParsedFiles(tagsFolder);
		new ParseMockarooTagJsonFiles(tagsFolder);
	}
}
