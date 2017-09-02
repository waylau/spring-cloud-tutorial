package com.waylau.spring.cloud.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 天气信息实体.
 * 
 * @since 1.0.0 2017年4月29日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class WeatherData implements Serializable {
	 
	private static final long serialVersionUID = 1L;
		
	private String city;
    private String aqi;
    private String wendu;
    private String ganmao;
	private YesterdayVO yesterday;
    private List<ForecastVO> forecast;
 
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getWendu() {
		return wendu;
	}
	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	public String getGanmao() {
		return ganmao;
	}
	public void setGanmao(String ganmao) {
		this.ganmao = ganmao;
	}
    public YesterdayVO getYesterday() {
		return yesterday;
	}
	public void setYesterday(YesterdayVO yesterday) {
		this.yesterday = yesterday;
	}
	public List<ForecastVO> getForecast() {
		return forecast;
	}
	public void setForecast(List<ForecastVO> forecast) {
		this.forecast = forecast;
	}
}
