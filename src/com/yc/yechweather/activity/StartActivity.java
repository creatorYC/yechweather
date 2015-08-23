package com.yc.yechweather.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yc.yechweather.R;
import com.yc.yechweather.model.City;

/**
 * ��������ʱ�Ľ���
 */
public class StartActivity extends Activity {

	private LocationClient locationClient;// ��λSDK�ĺ�����
	//ѡ�еĳ���
	private String selectedCity;
	private String cityName;
	// ��ǰ��λ����
	private TextView currentLoc;
	// ��λ��ť(���¶�λ)
	private Button reLocate;
	// ��Ӱ�ť(�����������)
	private Button add;
	// ���ð�ť
	private Button set;
	//
	private ListView cityListView;
	// ��ӵĳ����б�
	private List<String> cityList = new ArrayList<String>();
	// ListView ��������
	private ArrayAdapter<String> adapter;
	private static int CITY_NUMS;

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
				locationClient.stop();
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_layout);
		currentLoc = (TextView) findViewById(R.id.current_location);
		reLocate = (Button) findViewById(R.id.re_locate);
		add = (Button) findViewById(R.id.add);
		set = (Button) findViewById(R.id.set);

		cityListView = (ListView) findViewById(R.id.selected_city);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, cityList);
		cityListView.setAdapter(adapter);
		cityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedCity = cityList.get(position);
				locateToMain(selectedCity);
			}
		});
		reLocate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				locationClient.start();
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						ChooseAreaActivity.class);
				intent.putExtra("addCity", true);
				startActivity(intent);
				finish();
			}
		});
		//��ChooseAreaActivity ѡ���˳�����ת����
		if (getIntent().getBooleanExtra("add_success", false)) {
			cityName = getIntent().getStringExtra("add_this_city");
			cityList.add(cityName);
			adapter.notifyDataSetChanged();
			cityListView.setSelection(0);
		}
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
