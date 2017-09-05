# 提升微服务的并发访问能力

有时，为了提升整个网站的性能，我们会将经常需要访问数据缓存起来，这样，在下次查询的时候，能快速的找到这些数据。

缓存的使用与系统的时效性有着非常大的关系。当我们的系统时效性要求不高时，则选择使用缓存是极好的。当，系统要求的时效性比较高时，则并不适合用缓存。

本章节，我们将演示如何通过集成Redis服务器来进行数据的缓存，以提高微服务的并发访问能力。

天气数据接口，本身时效性不是很高，而且又因为是Web服务，在调用过程中，本身是存在延时的。所以，采用缓存，一方面可以有效减轻访问天气接口服务带来的延时问题，另一方面，也可以减轻天气接口的负担，提高并发访问量。

在`micro-weather-basic`的基础上，我们构建了一个`micro-weather-redis`项目，作为示例。

## 开发环境

* Spring Data Redis 1.5.6
* Redis 3.2.100 : <https://github.com/MicrosoftArchive/redis/releases>


## 项目配置

添加 Spring Data Redis 的依赖。

```groovy
// 依赖关系
dependencies {
	//...
 
	// 添加  Spring Data Redis 依赖
	compile('org.springframework.boot:spring-boot-starter-data-redis')

 	//...
}
```


## 下载安装、运行 Redis 


安装后，默认运行在 <localhost:6379> 地址端口。


## 修改 WeatherDataServiceImpl

增加了 StringRedisTemplate 用于操作 Redis。


```java
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
		
		if (!this.stringRedisTemplate.hasKey(key)) {
			System.out.println("Not Found key " + key);
			
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			

			if (response.getStatusCodeValue() == 200) {
				strBody = response.getBody();
			}

			ops.set(key, strBody, 10L, TimeUnit.SECONDS);
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
```

修改了 doGetWeatherData 方法，增加了 Redis 数据的判断。
* 当存在某个key（天气接口的uri，是唯一代表某个地区的天气数据）时，我们就从 Redis 里面拿缓存数据；
* 当不存在某个key（没有初始化数据或者数据过期了），从去天气接口里面去取最新的数据，并初始化到 Redis 中；
* 由于天气数据更新频率的特点（基本上一个小时或者半个小时更新一次），或者，我们在Redis里面设置了 30分钟的超时时间。

## 运行

多次访问某个天气接口时，来测试效果。

```
Not Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601, value={"data":{"yesterday":{"date":"5日星期二","high":"高温 32℃","fx":"无持续风向","low":"低温 27℃","fl":"<![CDATA[<3级]]>","type":"中雨"},"city":"深圳","aqi":"34","forecast":[{"date":"6日星期三","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"中雨"},{"date":"7日星期四","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"小雨"},{"date":"8日星期五","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"雷阵雨"},{"date":"9日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"无持续风向","type":"多云"},{"date":"10日星期天","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"无持续风向","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"27"},"status":1000,"desc":"OK"}
Not Found key http://wthrcdn.etouch.cn/weather_mini?citykey=101280601
```

可以看到，第一次访问接口时，没有找到 Redis 里面的数据，所以，就初始化了数据。
后面几次访问，都是访问 Redis 里面的数据。最后一次，由于超时了，所以 Redis 里面又没有数据了，所以又会拿天气接口的数据。

## 源码

本章节的源码，在`micro-weather-redis`目录下。