package android.rr.sendlater;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.rr.sendlater.utils.SendLaterConstants;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.telephony.SmsManager;
import android.util.Log;

public class SendMsgService extends JobIntentService {
    private final String TAG = SendMsgService.class.getSimpleName();

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e(TAG, "in onHandleWork.....intent: "+intent);

        registerForMsgReceivers();
        sendMsg(intent);
    }

    private void registerForMsgReceivers () {
        Log.e(TAG, "registerForMsgReceivers.....");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendLaterConstants.SMS_SENT);
        registerReceiver(msgSentReceiver, intentFilter);

        intentFilter = new IntentFilter();
        intentFilter.addAction(SendLaterConstants.SMS_DELIVERED);
        registerReceiver(msgDeliveredReceiver, intentFilter);
    }

    private void unregisterReceivers () {
        Log.e(TAG, "unregisterReceivers.....");
        try {
            if (null != msgSentReceiver)
                unregisterReceiver(msgSentReceiver);
            if (null != msgDeliveredReceiver)
                unregisterReceiver(msgDeliveredReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg (Intent intent) {
        Log.e(TAG, "sendMsg.....intent: "+intent);
        SmsManager manager = SmsManager.getDefault();

        PendingIntent piSend = PendingIntent.getBroadcast(this,
                SendLaterConstants.MSG_SENT_PENDING_INTENT_REQUEST_CODE,
                new Intent(SendLaterConstants.SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this,
                SendLaterConstants.MSG_DELIVERED_PENDING_INTENT_REQUEST_CODE,
                new Intent(SendLaterConstants.SMS_DELIVERED), 0);

//        String id = intent.getStringExtra(SendLaterConstants.ALARM_ROW_ID);
        String message = intent.getStringExtra(SendLaterConstants.ALARM_MSG);
        String numbers = intent.getStringExtra(SendLaterConstants.ALARM_NUMBERS);
//        long dateTimeInMillis = intent.getLongExtra(SendLaterConstants.ALARM_DATE_TIME_IN_MILLIS, 0);
        Log.e(TAG, "sendMsg, numbers: "+numbers);
        /*if(isBinary)
        {
            byte[] data = new byte[message.length()];

            for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
            {
                data[index] = (byte)message.charAt(index);
            }

            manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
        }
        else
        {
            int length = message.length();

            if(length > MAX_SMS_MESSAGE_LENGTH) //MAX_SMS_MESSAGE_LENGTH = 160
            {
                ArrayList<String> messagelist = manager.divideMessage(message);
                // if msg length is greater than MAX_SMS_MESSAGE_LENGTH, use multiparttextmsg
                manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
            }
            else
            {
                manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
            }
        }*/

        String[] multiNumbers = numbers.split(",");
        for (String phoneNumber : multiNumbers) {
            manager.sendTextMessage(phoneNumber, null, message, piSend, piDelivered);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "in onDestroy().....");
        unregisterReceivers();
    }

    private BroadcastReceiver msgSentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "msgSentReceiver, onReceive isCalled.. action: "+intent.getAction()+
                    ", getResultCode(): "+getResultCode());

            String message = null;

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = "Message sent!";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = "Error. Message not sent.";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = "Error: No service.";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = "Error: Null PDU.";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = "Error: Radio off.";
                    break;
            }
        }
    };

    private BroadcastReceiver msgDeliveredReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "msgDeliveredReceiver, onReceive isCalled.. action: "+intent.getAction()+
                    ", getResultCode(): "+getResultCode());
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    };
}