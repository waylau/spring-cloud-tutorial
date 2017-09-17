package com.waylau.spring.cloud.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * HelloClient Test.
 * 
 * @since 1.0.0 2017年9月17日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloClientTest {

	@Autowired
	private HelloClient helloClient;
	
	@Test
	public void testHello() {
		String hello = helloClient.getHello();
		System.out.println(hello);
	}

}
