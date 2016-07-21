package com.work;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.work.exceptions.JsonFormatException;
import com.work.service.DataParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * @author lilit on 7/22/16.
 */
public class TestDataParser {

	@Test
	public void testValidJsonParse() throws IOException {
		DataParser dataParser = new DataParser();
		URL url = Resources.getResource("validJson.json");
		String text = Resources.toString(url, Charsets.UTF_8);
		List<String> data = dataParser.parse(text);
		assertTrue("Loaded data must be one row", data.size() == 1);
	}

	@Test(expected = JsonFormatException.class)
	public void testMissingFieldJsonParse() throws IOException {
		DataParser dataParser = new DataParser();
		URL url = Resources.getResource("missingFieldJson.json");
		String text = Resources.toString(url, Charsets.UTF_8);
		dataParser.parse(text);
	}

	@Test(expected = JsonFormatException.class)
	public void testinvalidJsonParse() throws IOException {
		DataParser dataParser = new DataParser();
		URL url = Resources.getResource("invalidJson.json");
		String text = Resources.toString(url, Charsets.UTF_8);
		dataParser.parse(text);
	}
}
