package com.yc.yechweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {

	/**
	 * �ӷ�������ȡ����
	 * 
	 * @param address
	 * @param listener
	 */
	public static void sendHttpResquest(final String address,
			final HttpCallbackListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpURLConnection connection = null;
				try {
					System.out.println("-->" + address);
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					String response = "";
					if (connection.getResponseCode() == 200) {// �ж��������Ƿ���200�룬����ʧ��
						InputStream is = connection.getInputStream(); // ��ȡ������
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "utf-8"));
						String line = "";
						while ((line = reader.readLine()) != null) {
							response += line;
						}
					}

					if (listener != null) {
						// �ص� onFinish ����
						listener.onFinish(response);
					}

				} catch (Exception e) {
					// �ص� onError()����
					Log.i("data", "" + e);
					listener.onError(e);
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

}
