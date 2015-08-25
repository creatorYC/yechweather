package com.yc.yechweather.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yc.yechweather.R;
import com.yc.yechweather.util.Const;
import com.yc.yechweather.util.Utility;

/**
 * ��������ʱ�Ľ���
 */
public class StartActivity extends Activity {

	// ��λ��ť(���¶�λ)
	// private Button reLocate;
	// ��Ӱ�ť(�����������)
	private Button add;
	// ���ð�ť
	private Button set;
	//
	private ListView cityListView;
	// ��ӵĳ����б�
	private List<HashMap<String, Object>> cityList = new ArrayList<HashMap<String, Object>>();
	// ListView ��������
	private SimpleAdapter simpleAdapter;

	// ��ǰ��λ����
	private TextView currentLoc;

	// // �ж��Ƿ��Ǵ� WeatherActivity ��ת������(ͨ���л����а�ť)
	// private boolean isFromWeatherActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_layout);
		HashMap<String, Object> map = new HashMap<String, Object>();
		currentLoc = (TextView) findViewById(R.id.current_location);
		// reLocate = (Button) findViewById(R.id.re_locate);
		add = (Button) findViewById(R.id.add);
		set = (Button) findViewById(R.id.set);

		cityListView = (ListView) findViewById(R.id.selected_city);

		// ���ð�ť��������ڳ����б��ÿһ������ɾ��ͼ��
		set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			     //ȫѡ����ListView��ѡ�ÿ��ѡ����൱�ڲ��������ļ��е�RelativeLayout  
			     for(int i = 0; i < cityListView.getCount(); i++){  
			    	 RelativeLayout layout = 
			    			 (RelativeLayout) cityListView.getAdapter().getView(i, null, null);  
			    	 ImageView image = (ImageView) layout.getChildAt(0);
			    	 //image.setImageResource(R.drawable.delete);
			    	 image.setVisibility(View.VISIBLE);
			    	 
			     }
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						ChooseAreaActivity.class);
				intent.putExtra("addCity", true);
				intent.putExtra("isFromStartActivity", true);
				startActivity(intent);
				finish();
			}
		});
		cityList = loadCityList(StartActivity.this);
		simpleAdapter = new SimpleAdapter(this, cityList,// ��Ҫ�󶨵�����
				R.layout.city_list_item,// ÿһ�еĲ���
				// ��̬�����е�����Դ�ļ���Ӧ�����岼�ֵ�View��
				new String[] { "ItemImage", "ItemText" }, new int[] {
						R.id.ItemImage, R.id.ItemText });
		cityListView.setAdapter(simpleAdapter);// ΪListView��������
		// System.out.println("cityList--->" + cityList);
		// ��ChooseAreaActivity ѡ���˳�����ת����
		if (getIntent().getBooleanExtra("add_success", false)) {

			String cityName = getIntent().getStringExtra("add_this_city");
			map.put("ItemImage", R.drawable.delete);// ����ͼƬ
			map.put("ItemText", cityName);
			cityList.add(map);
			// ��������б�
			saveCityList(StartActivity.this, cityList);
			simpleAdapter.notifyDataSetChanged();
			cityListView.setSelection(0);
		}
		cityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedCity = (String) cityList.get(position).get(
						"ItemText");
				locateToMain(selectedCity);
			}
		});
		currentLoc.setText(Const.locatedCity);
		currentLoc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				locateToMain(Const.locatedCity);
			}
		});
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

	/**
	 * ��������б�
	 */
	public boolean saveCityList(Context context,
			List<HashMap<String, Object>> list) {
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putInt("Status_size", list.size());
		try {
			String listString = Utility.list2String(list);
			editor.putString("listString", listString);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return editor.commit();
	}

	/**
	 * ��ȡ�����б�
	 */
	public List<HashMap<String, Object>> loadCityList(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String listString = prefs.getString("listString", "");
		if (listString != "" && listString.length() > 0) {
			try {
				List<HashMap<String, Object>> list = Utility
						.string2List(listString);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cityList;
	}

	/**
	 * ���� back ���������ݵ�ǰ�ļ������жϣ���ʱӦ�÷������б�ʡ�б���ֱ���˳�
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, WeatherActivity.class);
		startActivity(intent);
		finish();
	}
}
