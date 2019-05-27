package android.rr.sendlater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.rr.sendlater.utils.SendLaterConstants;
import android.util.Log;

public class SendMsgAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = SendMsgAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive is called.....");

        startJobService (context, intent);
    }

    private void startJobService(Context context, Intent intent) {
//        ComponentName myServiceComponent = new ComponentName(context, SendMsgService.class);
        Intent myServiceIntent = new Intent(context, SendMsgService.class);
        myServiceIntent.putExtra(SendLaterConstants.ALARM_ROW_ID,
                intent.getStringExtra(SendLaterConstants.ALARM_ROW_ID));
        myServiceIntent.putExtra(SendLaterConstants.ALARM_MSG,
                intent.getStringExtra(SendLaterConstants.ALARM_MSG));
        myServiceIntent.putExtra(SendLaterConstants.ALARM_NUMBERS,
                intent.getStringExtra(SendLaterConstants.ALARM_NUMBERS));
        myServiceIntent.putExtra(SendLaterConstants.ALARM_DATE_TIME_IN_MILLIS,
                intent.getStringExtra(SendLaterConstants.ALARM_DATE_TIME_IN_MILLIS));
        context.startService(myServiceIntent);
    }
}