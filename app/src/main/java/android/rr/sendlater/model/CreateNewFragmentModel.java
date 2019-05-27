package android.rr.sendlater.model;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.rr.sendlater.db.SendLaterDB;
import android.rr.sendlater.presenter.CreateNewFragmentPresenter;
import android.rr.sendlater.utils.SendLaterConstants;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class CreateNewFragmentModel implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private CreateNewFragmentPresenter mCreateNewFragmentPresenter;
    public CreateNewFragmentModel (CreateNewFragmentPresenter createNewFragmentPresenter) {
        mCreateNewFragmentPresenter = createNewFragmentPresenter;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCreateNewFragmentPresenter.chosenDate(dayOfMonth, month, year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Calendar calendar = Calendar.getInstance();
        final Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedTime.set(Calendar.MINUTE, minute);

        if (selectedTime.after(calendar))
            mCreateNewFragmentPresenter.chosenTime(hourOfDay, minute);
        else
            mCreateNewFragmentPresenter.selectedInvalidTime();
    }

    public void openContactsApp(Context context) {
        Intent phonebookIntent = new Intent("intent.action.INTERACTION_TOPMENU");
        phonebookIntent.putExtra("additional", "phone-multi");
        phonebookIntent.putExtra("maxRecipientCount", 10);
        phonebookIntent.putExtra("FromMMS", true);
        ((Activity)context).startActivityForResult(phonebookIntent,
                SendLaterConstants.MULTI_SELECT_CONTACTS_REQUEST_CODE);
        /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        ((Activity)context).startActivityForResult(intent, 110);*/
    }

    public void selectMultipleContacts (CreateNewFragment createNewFragment) {
        createNewFragment.startActivityForResult(
                new Intent("android.rr.sendlater.MULTI_CONTACTS_PICKER"),
                SendLaterConstants.MULTI_SELECT_CONTACTS_REQUEST_CODE);
    }

    public void saveTheMsgToDB (Context context, String msg, String numbers, long dateTimeInMills) {
        long rowInsert = new SendLaterDB(context).insertMsgData(numbers, msg, dateTimeInMills,
                String.valueOf(false));
        if (rowInsert > 0) {
            List<SendMsgLaterModel> sendMsgLaterModels = new SendLaterDB(context).
                    getMsgsDataByIdOrMillis(String.valueOf(dateTimeInMills), false);

            mCreateNewFragmentPresenter.showToast(context.getString(R.string.msgSaved));
            mCreateNewFragmentPresenter.setAlarmToSavedMsg(sendMsgLaterModels.get(0).getId(),
                    sendMsgLaterModels.get(0).getEnteredMsg(),
                    sendMsgLaterModels.get(0).getPhoneNumbers(),
                    sendMsgLaterModels.get(0).getDateTimeInMills());
        }
        else
            mCreateNewFragmentPresenter.showToast(context.getString(R.string.msgNotSaved));
//        mCreateNewFragmentPresenter.resetTheFields();
    }

    public interface CreateNewFragModel {
        void chosenDate (int dayOfMonth, int month, int year);
        void chosenTime (int hourOfDay, int minute);
        void selectedInvalidTime();
        void setAlarmToSavedMsg(String id, String msg, String numbers, long dateTimeInMills);
    }

}