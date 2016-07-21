package com.work.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.work.exceptions.JsonFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for parsing data.
 *
 * @author lilit on 7/22/16.
 */
public class DataParser {

	private static final Logger logger = LogManager.getLogger(DataParser.class);

	/**
	 * @param json json array
	 * @return array of parsed data as rows.
	 */
	public List<String> parse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> rows = new ArrayList<>();
		ArrayNode actualObj = null;
		try {
			actualObj = (ArrayNode) mapper.readTree(json);
		} catch (IOException e) {
			logger.error(String.format("Cant parse %s JSON", json), e);
			throw new JsonFormatException(String.format("Cant parse %s JSON", json), e);
		}
		actualObj.forEach(jsonNode -> {
			StringBuilder rowBuilder = new StringBuilder();
			rowBuilder.append(getRequired(jsonNode, "_id")).append(",");
			rowBuilder.append(getRequired(jsonNode, "name")).append(",");
			rowBuilder.append(getRequired(jsonNode, "type")).append(",");
			JsonNode geoNode = jsonNode.get("geo_position");
			rowBuilder.append(getRequired(geoNode, "latitude")).append(",");
			rowBuilder.append(getRequired(geoNode, "longitude"));
			rows.add(rowBuilder.toString());
		});
		return rows;
	}

	private String getRequired(JsonNode node, String fieldName) throws JsonFormatException {
		JsonNode result = node.get(fieldName);
		if (result == null) {
			throw new JsonFormatException(String.format("There isn't field with %s name", fieldName));
		}

		return result.asText();
	}
}
