/**
 * 
 */
package com.waylau.spring.cloud.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_API + "?citykey=" + cityId;
		return this.doGetWeatherData(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_API + "?city=" + cityName;
		return this.doGetWeatherData(uri);
	}

	/**
	 * 获取天气数据
	 * @param uri
	 * @return
	 */
	private WeatherResponse doGetWeatherData(String uri) {
		
		ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
		String key = uri;
		WeatherResponse weather = null;
		String strBody = null;
		
		// Redis 里有数据就取 Redis 的数据，否则取天气接口的数据
		if (!this.stringRedisTemplate.hasKey(key)) {
			System.out.println("Not Found key " + key);
			
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			

			if (response.getStatusCodeValue() == 200) {
				strBody = response.getBody();
			}

			ops.set(key, strBody, 30L, TimeUnit.MINUTES); // 超时时间
		} else {
			System.out.println("Found key " + key + ", value=" + ops.get(key));
			
			strBody = ops.get(key);
		}
 
		ObjectMapper mapper = new ObjectMapper();
	
		try {
			weather = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;
	}

}
