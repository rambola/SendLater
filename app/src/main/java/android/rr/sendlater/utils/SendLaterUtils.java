package android.rr.sendlater.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.rr.sendlater.SendMsgAlarmReceiver;
import android.util.Log;

public class SendLaterUtils {
    private final String TAG = SendLaterUtils.class.getSimpleName();
    private Context mContext;

    public SendLaterUtils (Context context) {
        mContext = context;
    }

    public void setAlarmToMsg (String msg, String numbers, long dateTimeInMills) {
        Log.e(TAG, "setAlarmToMsg, msg: "+msg+", numbers: "+numbers+
                ", dateTimeInMillis: "+dateTimeInMills );
        Intent intent = new Intent(mContext, SendMsgAlarmReceiver.class);
        intent.putExtra(SendLaterConstants.ALARM_MSG, msg);
        intent.putExtra(SendLaterConstants.ALARM_NUMBERS, numbers);
        intent.putExtra(SendLaterConstants.ALARM_DATE_TIME_IN_MILLIS, dateTimeInMills);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                SendLaterConstants.ALARM_PENDING_INTENT_REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateTimeInMills, pendingIntent);
    }

    public void cancelAlarm () {

    }

}
