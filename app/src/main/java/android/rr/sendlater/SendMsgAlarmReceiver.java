package android.rr.sendlater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SendMsgAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = SendMsgAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive is called.....");
    }

}