package com.waylau.spring.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Hello Client
 * 
 * @since 1.0.0 2017年9月17日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@FeignClient("micro-weather-eureka-client")
public interface HelloClient {
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
    String getHello();
}
