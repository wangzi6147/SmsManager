package com.example.smsmanager.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.example.smsmanager.Dao.InterceptDao;
import com.example.smsmanager.Dao.SmsDao;
import com.example.smsmanager.bean.SmsInfoBean;

public class SmsReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		InterceptDao dao = new InterceptDao(context);
		SmsDao smsDao =  new SmsDao(context);
		List<SmsMessage> smss = parseSmss(context, intent);
		boolean bRes = false;
		for (SmsMessage sms : smss)
		{
			bRes |= dao.isContainNumber(sms.getOriginatingAddress());
			bRes |= dao.isContainSensitiveWord(sms.getMessageBody());
			if (bRes)
			{
				abortBroadcast();
				SmsInfoBean bean = new SmsInfoBean();
				bean.setName("");
				bean.setPhoneNumber(sms.getOriginatingAddress());
				bean.setDate("");
				bean.setSmsbody(sms.getMessageBody());
				bean.setType("");
				bean.set_id("");
				smsDao.add(bean);
				
				return;
			}
		}
	}

	private List<SmsMessage> parseSmss(Context context, Intent intent)
	{
		Object[] objPdus = (Object[]) intent.getExtras().get("pdus");
		ArrayList<SmsMessage> messages = new ArrayList<SmsMessage>();
		for (Object objPdu : objPdus)
		{
			SmsMessage message = SmsMessage
					.createFromPdu((byte[]) objPdu);
			messages.add(message);
		}
		return messages;
	}

}
