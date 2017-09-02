/**
 * 
 */
package com.waylau.spring.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waylau.spring.cloud.vo.WeatherResponse;

/**
 * 天气数据服务.
 * 
 * @since 1.0.0 2017年9月2日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	
    @Autowired
    private RestTemplate restTemplate;
    
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + cityId;
		ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(uri, WeatherResponse.class);
		
		return null;
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		// TODO Auto-generated method stub
		return null;
	}

}
