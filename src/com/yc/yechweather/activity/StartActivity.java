package com.yc.yechweather.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yc.yechweather.R;
import com.yc.yechweather.util.Const;

/**
 * ��������ʱ�Ľ���
 */
public class StartActivity extends Activity {
	
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

	// ��ǰ��λ����
	private TextView currentLoc;
//	// �ж��Ƿ��Ǵ� WeatherActivity ��ת������(ͨ���л����а�ť)
//	private boolean isFromWeatherActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		reLocate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
			String cityName = getIntent().getStringExtra("add_this_city");
			cityList.add(cityName);
			adapter.notifyDataSetChanged();
			cityListView.setSelection(0);
		}
		cityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedCity = cityList.get(position);
				locateToMain(selectedCity);
			}
		});
//		isFromWeatherActivity = getIntent().getBooleanExtra(
//				"from_weather_activity", false);
//		if(isFromWeatherActivity){
			currentLoc.setText(Const.locatedCity);
//		}
	}

	/**
	 * ��λ��ֱ�Ӳ�ѯ��Ӧ���е�������Ϣ
	 */
	private void locateToMain(String cityName) {
		Intent intent = new Intent(StartActivity.this, WeatherActivity.class);
		intent.putExtra("city_name", cityName);
		intent.putExtra("isLocated", true);
		intent.putExtra("isAddCity", true);
		startActivity(intent);
		finish();
	}

}
