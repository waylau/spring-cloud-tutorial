/**
 * 
 */
package com.waylau.spring.cloud.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherDataServiceTests {

	@Autowired
	private WeatherDataService weatherDataService;
	
	@Test
	public void testGetDataByCityId() {
		weatherDataService.getDataByCityId("101280601");
	}
}
