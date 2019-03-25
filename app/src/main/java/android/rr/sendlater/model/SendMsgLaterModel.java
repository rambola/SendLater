package android.rr.sendlater.model;

public class SendMsgLaterModel {
    private String mEnteredMsg;
    private long mDateTimeInMills;
    private String mIsMsgSent;

    public SendMsgLaterModel (String enteredMsg, long dateTimeInMills, String isMsgSent) {
        mEnteredMsg = enteredMsg;
        mDateTimeInMills = dateTimeInMills;
        mIsMsgSent = isMsgSent;
    }

    public String getEnteredMsg () {
        return mEnteredMsg;
    }

    public long getDateTimeInMills () {
        return mDateTimeInMills;
    }

    public String getIsMsgSent () {
        return mIsMsgSent;
    }

}