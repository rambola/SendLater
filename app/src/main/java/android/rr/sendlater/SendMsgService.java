package android.rr.sendlater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendMsgService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}