package com.waylau.spring.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
		String body = this.restTemplate.getForObject("http://wthrcdn.etouch.cn/weather_mini?citykey=101280601", String.class);
		System.out.print(body);
	}

}
