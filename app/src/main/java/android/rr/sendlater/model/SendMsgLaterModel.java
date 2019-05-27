package android.rr.sendlater.model;

public class SendMsgLaterModel {
    private String mId;
    private String mPhoneNumbers;
    private String mEnteredMsg;
    private long mDateTimeInMills;
    private String mIsMsgSent;

    public SendMsgLaterModel (String id, String phoneNumbers, String enteredMsg,
                              long dateTimeInMills, String isMsgSent) {
        mId = id;
        mPhoneNumbers = phoneNumbers;
        mEnteredMsg = enteredMsg;
        mDateTimeInMills = dateTimeInMills;
        mIsMsgSent = isMsgSent;
    }

    public String getPhoneNumbers () {
        return mPhoneNumbers;
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

    public String getId () { return mId; }
}