package android.rr.sendlater.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.rr.sendlater.model.SendMsgLaterModel;

import java.util.ArrayList;
import java.util.List;

public class SendLaterDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "SendLater.db";
    private static final int DB_VERSION = 1;

    private final Context mContext;

    private final String TABLE_NAME = "SendLaterTable";
    private final String COLUMN_ID = "id";
    private final String COLUMN_PHONE_NUMBERS = "PhoneNumbers";
    private final String COLUMN_MSG = "EnteredMessage";
    private final String COLUMN_DATE_TIME_IN_MILLS = "DateTimeInMills";
    private final String COLUMN_MSG_SENT = "MsgSent";

    public SendLaterDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ("+COLUMN_ID+" integer primary key, "+
                COLUMN_PHONE_NUMBERS+" text, "+COLUMN_MSG+" text, "+
                COLUMN_DATE_TIME_IN_MILLS+" long, "+COLUMN_MSG_SENT+"text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertMsgData (String phoneNumbers, String enteredMsg, long dateTimeInMills, String isMsgSent) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHONE_NUMBERS, phoneNumbers);
        contentValues.put(COLUMN_MSG, enteredMsg);
        contentValues.put(COLUMN_DATE_TIME_IN_MILLS, dateTimeInMills);
        contentValues.put(COLUMN_MSG_SENT, isMsgSent);

        sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<SendMsgLaterModel> getMsgsData (String filterType) {
        List<SendMsgLaterModel> sendMsgLaterModels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String whereArgs[] = {filterType};

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, COLUMN_MSG_SENT, whereArgs,
                null, null, COLUMN_DATE_TIME_IN_MILLS);

        if (null != cursor) {
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                sendMsgLaterModels.add(new SendMsgLaterModel(
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBERS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MSG)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_DATE_TIME_IN_MILLS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MSG_SENT))));
            }
        }

        return  sendMsgLaterModels;
    }

    public void deleteMsg (long dateTimeInMills) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] whereArgs = {Long.toString(dateTimeInMills)};
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_DATE_TIME_IN_MILLS, whereArgs);
    }

}