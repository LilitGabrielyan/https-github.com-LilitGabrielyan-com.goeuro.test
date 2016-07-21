package com.work.service;

import com.work.exceptions.DataFetchingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Class for retrieving data of city from external resource.
 *
 * @author lilit on 7/21/16.
 */
public class DataRetriever {

	private final RestTemplate restTemplate;

	private static final Logger logger = LogManager.getLogger(DataRetriever.class);

	@Value("${app.city.data.url}")
	private String dataUrl;

	@Autowired
	public DataRetriever(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Retrieve information of city from external resources and save it as csv file.
	 *
	 * @param city name of city which data should be downloaded.
	 */
	public String downloadCityInfo(String city) throws DataFetchingException {
		String requestURL = this.dataUrl + city;
		String responseData;
		try {
			responseData = restTemplate.getForObject(requestURL, String.class);
		} catch (RestClientException | IllegalArgumentException ex) {
			logger.error(String.format("Could not connect to %s", requestURL), ex);
			throw new DataFetchingException(String.format("Could not connect to %s", requestURL), ex);
		}
		return responseData;
	}


}
