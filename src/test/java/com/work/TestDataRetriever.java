package com.work;

import com.google.common.base.Strings;
import com.work.exceptions.DataFetchingException;
import com.work.service.DataRetriever;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertTrue;

/**
 * @author lilit on 7/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestDataRetriever {

	@Autowired
	private DataRetriever dataRetriever;

	@Test
	public void testRetrieveFromValidUrl() {
		ReflectionTestUtils.setField(dataRetriever, "dataUrl", "http://api.goeuro.com/api/v2/position/suggest/en/");
		String data = dataRetriever.downloadCityInfo("Berlin");
		assertTrue("Response exist", !Strings.isNullOrEmpty(data));
	}

	@Test(expected = DataFetchingException.class)
	public void testRetrieveFromInValidUrl() {
		ReflectionTestUtils.setField(dataRetriever, "dataUrl", "test/incorrect/url");
		dataRetriever.downloadCityInfo("Berlin");
	}
}
