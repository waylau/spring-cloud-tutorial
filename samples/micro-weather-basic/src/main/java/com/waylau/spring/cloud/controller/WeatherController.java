/**
 * 
 */
package com.waylau.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waylau.spring.cloud.service.WeatherDataService;
import com.waylau.spring.cloud.vo.WeatherResponse;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/weather")	
public class WeatherController {

	@Autowired
	private WeatherDataService weatherDataService;
	
	@GetMapping("/cityId/{cityId}")
	public WeatherResponse getReportByCityId(@RequestParam(value = "name", required = false, defaultValue = "101280601") String cityId) {
		return weatherDataService.getDataByCityId(cityId);
	}

}
