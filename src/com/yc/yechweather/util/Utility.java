package com.yc.yechweather.util;

import android.text.TextUtils;

import com.yc.yechweather.model.City;
import com.yc.yechweather.model.County;
import com.yc.yechweather.model.Province;
import com.yc.yechweather.model.YechWeatherDB;

/**
 * �����ӷ��������ص�����
 * @author Administrator
 *
 */
public class Utility {
	
	/**
	 * �����ʹ�����������ص�ʡ������
	 */
	public synchronized static boolean handleProvincesResponse(YechWeatherDB 
			yechWeatherDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String p : allProvinces){
					String[] arr = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(arr[0]);
					province.setProvinceName(arr[1]);
					//���������������ݴ洢�� Province ��
					yechWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ʹ�����������ص��м�����
	 */
	public synchronized static boolean handleCitiesResponse(YechWeatherDB 
			yechWeatherDB,String response,int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for(String c : allCities){
					String[] arr = c.split("\\|");
					City city = new City();
					city.setCityCode(arr[0]);
					city.setCityName(arr[1]);
					city.setProvinceId(provinceId);
					//���������������ݴ洢�� City ��
					yechWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ʹ�����������ص��ؼ�����
	 */
	public synchronized static boolean handleCountiesResponse(YechWeatherDB 
			yechWeatherDB,String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length > 0){
				for(String c : allCounties){
					String[] arr = c.split("\\|");
					County county = new County();
					county.setCountyCode(arr[0]);
					county.setCountyName(arr[1]);
					county.setCityId(cityId);
					//���������������ݴ洢�� County ��
					yechWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
