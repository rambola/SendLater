package android.rr.sendlater.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.rr.sendlater.model.CreateNewFragmentModel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class CreateNewFragmentPresenter implements View.OnClickListener, TextWatcher,
        CreateNewFragmentModel.CreateNewFragModel {
    private CreateNewFragment mCreateNewFragment;
    private CreateNewFragmentModel mCreateNewFragmentModel;

    public CreateNewFragmentPresenter (CreateNewFragment createNewFragment) {
        mCreateNewFragment = createNewFragment;
        mCreateNewFragmentModel = new CreateNewFragmentModel(CreateNewFragmentPresenter.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgTV:
                mCreateNewFragment.showCreateNewLayout();
                break;
            case R.id.cancelBtn:
                resetTheFields();
                hideKeyboard();
                break;
            case R.id.saveBtn:
                hideKeyboard();
                break;
            case R.id.selectedDataTV:
                showDatePickerDialog();
                break;
            case R.id.selectedTimeTV:
                showTimePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog () {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mCreateNewFragment.getActivity(),
                mCreateNewFragmentModel, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle(mCreateNewFragment.getResources().getString(R.string.chooseDate));
        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showTimePickerDialog () {
        Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mCreateNewFragment.getActivity(),
                mCreateNewFragmentModel, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(mCreateNewFragment.getResources().getString(R.string.chooseTime));
        mTimePicker.show();
    }

    private void resetTheFields () {
        mCreateNewFragment.resetViews();
    }

    private void hideKeyboard () {
        mCreateNewFragment.hideKeyBoard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCreateNewFragment.msgCharCount(s.length());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void chosenDate(int dayOfMonth, int month, int year) {
        mCreateNewFragment.selectedDate(dayOfMonth, month, year);
    }

    @Override
    public void chosenTime(int hourOfDay, int minute) {
        mCreateNewFragment.selectedTime(hourOfDay, minute);
    }

    @Override
    public void selectedInvalidTime() {
        showToast(mCreateNewFragment.getActivity().getResources().getString(
                R.string.errorMsgForTime));
    }

    public void showToast (String toastMsg) {
        Toast.makeText(mCreateNewFragment.getActivity().getApplicationContext(), toastMsg,
                Toast.LENGTH_LONG).show();
    }

    public interface CreateNewFragmentView {
        void showCreateNewLayout();
        void msgCharCount(int charCount);
        void selectedDate(int dayOfMonth, int month, int year);
        void selectedTime(int hourOfDay, int minute);
        void resetViews();
        void hideKeyBoard();
    }
}