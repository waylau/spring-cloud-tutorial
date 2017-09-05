package com.waylau.spring.cloud.service;

import com.waylau.spring.cloud.vo.WeatherResponse;

/**
 * 天气数据服务.
 * 
 * @since 1.0.0 2017年9月2日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface WeatherDataService {

	/**
	 * 根据城市ID查询天气数据
	 * @param cityId
	 * @return
	 */
	WeatherResponse getDataByCityId(String cityId);
	
	/**
	 * 根据城市名称查询天气数据
	 * @param cityId
	 * @return
	 */
	WeatherResponse getDataByCityName(String cityName);
}
