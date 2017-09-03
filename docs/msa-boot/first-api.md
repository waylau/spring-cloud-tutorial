# 基于 Spring Boot 的一个微服务实现


本章节，我们将基于 Spring Boot 技术来实现我们的第一个微服务天气预报应用——micro-weather。micro-weather 的作用是实现天气预报功能，可以根据不同的城市，查询该城市的实时天气情况。

## 开发环境

* Gradle 4.0
* Spring Boot 1.5.6

## 数据来源

理论上，天气的数据是天气预报的实现基础。本应用与实际的天气数据无关，理论上，可以兼容多种数据来源。但为求简单，我们在网上找了一个免费、可用的天气数据接口。

* 天气数据来源为中国天气网 <http://www.weather.com.cn/>。
* 城市ID列表。每个城市都有一个唯一的ID作为标识。见 <http://cj.weather.com.cn/support/Detail.aspx?id=51837fba1b35fe0f8411b6df> 或者 <http://mobile.weather.com.cn/js/citylist.xml>。

天气调用，接口示例，我们以“深圳”城市为例，访问：

* 通过城市名字获得天气数据 ：<http://wthrcdn.etouch.cn/weather_mini?city=深圳>
* 通过城市id获得天气数据：<http://wthrcdn.etouch.cn/weather_mini?citykey=101280601>

可用看到如下天气数据返回。

```json
{
    "data": {
        "yesterday": {
            "date": "1日星期五",
            "high": "高温 33℃",
            "fx": "无持续风向",
            "low": "低温 26℃",
            "fl": "<![CDATA[<3级]]>",
            "type": "多云"
        },
        "city": "深圳",
        "aqi": "72",
        "forecast": [
            {
                "date": "2日星期六",
                "high": "高温 32℃",
                "fengli": "<![CDATA[<3级]]>",
                "low": "低温 26℃",
                "fengxiang": "无持续风向",
                "type": "阵雨"
            },
            {
                "date": "3日星期天",
                "high": "高温 29℃",
                "fengli": "<![CDATA[5-6级]]>",
                "low": "低温 26℃",
                "fengxiang": "无持续风向",
                "type": "大雨"
            },
            {
                "date": "4日星期一",
                "high": "高温 29℃",
                "fengli": "<![CDATA[3-4级]]>",
                "low": "低温 26℃",
                "fengxiang": "西南风",
                "type": "暴雨"
            },
            {
                "date": "5日星期二",
                "high": "高温 31℃",
                "fengli": "<![CDATA[<3级]]>",
                "low": "低温 27℃",
                "fengxiang": "无持续风向",
                "type": "阵雨"
            },
            {
                "date": "6日星期三",
                "high": "高温 32℃",
                "fengli": "<![CDATA[<3级]]>",
                "low": "低温 27℃",
                "fengxiang": "无持续风向",
                "type": "阵雨"
            }
        ],
        "ganmao": "风较大，阴冷潮湿，较易发生感冒，体质较弱的朋友请注意适当防护。",
        "wendu": "29"
    },
    "status": 1000,
    "desc": "OK"
}
```

我们通过观察数据，来了解每个返回字段的含义。

* "city": 城市名称
* "aqi": 空气指数,
* "wendu": 实时温度
* "date": 日期，包含未来5天
* "high":最高温度
* "low": 最低温度
* "fengli": 风力
* "fengxiang": 风向
* "type": 天气类型

以上数据，是我们需要的天气数据的核心数据，但是，同时也要关注下面两个字段：

* "status": 接口调用的返回状态，返回值“1000”,意味着数据是接口正常
* "desc": 接口状态的描述，“OK”代表接口正常

重点关注返回值不是“1000”的情况,说明，这个接口调用异常了。

## 初始化一个 Spring Boot 项目

初始化一个 Spring Boot 项目 micro-weather-basic，该项目可以直接在我们之前章节课程中的 basic-gradle 项目基础进行修改。同时，为了优化项目的构建速度，我们对Maven中央仓库地址和 Gradle Wrapper 地址做了调整。其中细节暂且不表，读者可以自行参阅源码，或者学习笔者所著的《Spring Boot 教程》（<https://github.com/waylau/spring-boot-tutorial>）。其原理，我也整理到我的博客中了：

* https://waylau.com/change-gradle-wrapper-distribution-url-to-local-file/
* https://waylau.com/use-maven-mirrors/



## 创建天气信息实体 WeatherInfo


创建`com.waylau.spring.cloud.domain`包，用于存储实体。创建天气信息实体 WeatherInfo




## 访问API

* <http://localhost:8080/weather/cityId/101280601>
* <http://localhost:8080/weather/cityName/惠州>

## 参考

* http://blog.csdn.net/Sugar_tea/article/details/45224109
* http://blog.csdn.net/chenchunlin526/article/details/71439039
* https://stackoverflow.com/questions/34415144/cannot-parse-gzip-encoded-response-with-resttemplate-from-spring-web