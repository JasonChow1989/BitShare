package com.bashi_group_01.www.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.bashi_group_01.www.childactivity.FileMakeActivity;
import com.bashi_group_01.www.childactivity.MeetnotiyActivity;

/**
 * �������͵Ĺ㲥������;
 * 
 * @author share
 * 
 */
public class MyJPushReceiver extends BroadcastReceiver {

	private static final String FAWEN_JUMPCODE = "1";
	private static final String MEETTING_JUMPCODE = "2";
	private static final String HETONG_JUMPCODE = "3";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		System.out.println("action_-----------------" + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("�յ����Զ�����Ϣ����Ϣ�����ǣ�"
					+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// �Զ�����Ϣ����չʾ��֪ͨ������ȫҪ������д����ȥ����
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("�յ���֪ͨ");
			// �����������Щͳ�ƣ�������Щ��������
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			System.out.println("�û��������֪ͨ");
			// ����������Լ�д����ȥ�����û���������Ϊ

			String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
			System.out.println(type);
			try {
				JSONObject jsonObject = new JSONObject(type);
				String str = jsonObject.getString("key");
				String id = jsonObject.getString("tbl");
				if (str.equals(FAWEN_JUMPCODE)) {
					// ���Զ����Activity
					System.out.println("---------ǩ������ת---------");
					Intent i = new Intent(context, FaWenDetailActivity.class);
					// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("id", id);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				} else if (str.equals(MEETTING_JUMPCODE)) {
					System.out.println("-----------�������ת-----------");
					Intent i = new Intent(context, MeetingNotieItemActivitty.class); // �Զ���򿪵Ľ���
					i.putExtra("id", id);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				} else if (str.equals(HETONG_JUMPCODE)) {
					System.out.println("---------��ͬ����ת---------");
					Intent i = new Intent(context, EventHeTongDetailActivity.class);
					i.putExtra("id", id);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.d("JPush", "Unhandled intent - " + intent.getAction());
		}
	}
}
