package com.yc.yechweather.receiver;

import com.yc.yechweather.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//һ�������� AutoUpdateService���ͻ��� onStartCommand �������趨
		//һ����ʱ����������8Сʱ�� AutoUpdateReceiver �� onReceive ����
		//�ͻ�õ�ִ�У�������������ٴ����� AutoUpdateService���γ�ѭ��
		Intent i = new Intent(context,AutoUpdateService.class);
		context.startActivity(i);
	}

}
