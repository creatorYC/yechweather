package com.yc.yechweather.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yc.yechweather.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * ��������ʱ�Ľ���
 */
public class StartActivity extends Activity {

	private LocationClient locationClient;// ��λSDK�ĺ�����
	private String cityName;
	// ��ǰ��λ����
	private TextView currentLoc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationClient = new LocationClient(getApplicationContext());
		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// ͨ��GPS��λ
					cityName = location.getCity();
					cityName = cityName.substring(0, cityName.length() - 1);
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ͨ���������Ӷ�λ
					cityName = location.getCity();
					cityName = cityName.substring(0, cityName.length() - 1);
				}
				Log.i("data", "--" + cityName);
				currentLoc.setText(cityName);
				currentLoc.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						locateToMain(cityName);
					}
				});
//				
			}
		}); // ע���������
		initLocation();
		locationClient.start();
		setContentView(R.layout.start_layout);
		currentLoc = (TextView) findViewById(R.id.current_location);
	}


	/**
	 * ��λ��ֱ�Ӳ�ѯ��Ӧ���е�������Ϣ
	 */
	public void locateToMain(String cityName) {
		Intent intent = new Intent(StartActivity.this, WeatherActivity.class);
		intent.putExtra("city_name", cityName);
		intent.putExtra("isLocated", true);
		startActivity(intent);
		finish();
	}

	/**
	 * ��ʼ����λ��Ϣ
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ø߾��ȶ�λ��λģʽ
		option.setCoorType("bd09ll");// ���ðٶȾ�γ������ϵ��ʽ
		// option.setScanSpan(1000);// ���÷���λ����ļ��ʱ��Ϊ1000ms
		option.setIsNeedAddress(true);// �������þ���λ�ã�ֻ�����綨λ�ſ���
		locationClient.setLocOption(option);
	}

}
