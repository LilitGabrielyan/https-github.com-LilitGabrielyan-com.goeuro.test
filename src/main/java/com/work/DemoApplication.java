package com.work;

import com.work.exceptions.DataFetchingException;
import com.work.exceptions.JsonFormatException;
import com.work.service.DataParser;
import com.work.service.DataRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@SpringBootApplication
@PropertySource("classpath:config.properties")
public class DemoApplication {

	private static final Logger logger = LogManager.getLogger(DataRetriever.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		if (args.length != 1) {
			logger.error("City name parameter is reqiered!!!");
			System.exit(1);
		}
		String cityName = args[0];
		DataRetriever dataRetriever = context.getBean(DataRetriever.class);
		DataParser dataParser = context.getBean(DataParser.class);

		String response = fetchData(cityName, dataRetriever);
		List<String> dataInRows = parseData(response, dataParser);
		writeDataToFile(dataInRows, Paths.get(cityName + ".csv"), StandardCharsets.UTF_8);

	}

	private static String fetchData(String cityName, DataRetriever dataRetriever) {
		String response = null;
		try {
			response = dataRetriever.downloadCityInfo(cityName);
		} catch (DataFetchingException ex) {
			logger.error("Error while connecting to server!!!", ex.getMessage());
			logger.debug(ex);
			System.exit(1);
		}
		return response;
	}

	private static List<String> parseData(String data, DataParser dataParser) {
		List<String> dataInRows = null;
		try {
			dataInRows = dataParser.parse(data);
		} catch (JsonFormatException ex) {
			logger.error("Error while processing Json result from server!!!", ex.getMessage());
			logger.debug(ex);
			System.exit(1);
		}
		return dataInRows;
	}

	private static void writeDataToFile(List<String> data, Path filePath, Charset charset) {
		try {
			Files.write(filePath, data, charset, StandardOpenOption.CREATE);
		} catch (IOException e) {
			logger.error(String.format("Error while creating file in %s path. \n%s", filePath, e.getCause()));
			logger.error(e.getMessage(), e);
			System.exit(1);
		}
	}
}
