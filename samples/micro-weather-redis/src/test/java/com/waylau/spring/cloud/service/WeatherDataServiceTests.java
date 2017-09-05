package com.waylau.spring.cloud.service;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.waylau.spring.cloud.vo.WeatherResponse;
/**
 * 天气服务测试.
 * 
 * @since 1.0.0 2017年9月4日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherDataServiceTests {

	@Autowired
	private WeatherDataService weatherDataService;
	
	@Test
	public void testGetDataByCityId() {
		WeatherResponse response = weatherDataService.getDataByCityId("101280601");
		System.out.println(response.getDesc());
	}
}
