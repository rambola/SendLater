package android.rr.sendlater.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.rr.sendlater.model.CreateNewFragmentModel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateNewFragmentPresenter implements View.OnClickListener, TextWatcher,
        CreateNewFragmentModel.CreateNewFragModel, View.OnTouchListener {
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
            case R.id.selectContactsTV:
                //mCreateNewFragmentModel.openContactsApp(mCreateNewFragment.getActivity());
                mCreateNewFragmentModel.selectMultipleContacts(mCreateNewFragment);
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

    public ShapeDrawable getTextViewBackgroundDrawable () {
        // Initializing a ShapeDrawable
        ShapeDrawable sd = new ShapeDrawable();

        // Specify the shape of ShapeDrawable
        sd.setShape(new RectShape());

        // Specify the border color of shape
        sd.getPaint().setColor(mCreateNewFragment.getActivity().getResources().
                getColor(R.color.colorPrimaryDark));

        // Set the border width
        sd.getPaint().setStrokeWidth(7f);

        // Specify the style is a Stroke
        sd.getPaint().setStyle(Paint.Style.STROKE);

        return  sd;
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
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        final int DRAWABLE_LEFT = 0;
        //final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        //final int DRAWABLE_BOTTOM = 3;

        Log.e("raja", "clicked on delete img 1");
        Log.e("raja", "clicked on delete img 1: event action up: "+(event.getAction() == MotionEvent.ACTION_UP));
        Log.e("raja", "clicked on delete img 1: event action down: "+(event.getAction() == MotionEvent.ACTION_DOWN));
        Log.e("raja", "clicked on delete img 1: getrawx(): event.getRawX(): "+event.getRawX());
        Log.e("raja", "clicked on delete img 1: getrawx(): v.getRight: "+v.getRight());
        Log.e("raja", "clicked on delete img 1: getrawx(): v.getLeft: "+v.getLeft());
        Log.e("raja", "clicked on delete img 1: compounddrawabel right: "+(v.getRight() -((TextView) v).getCompoundDrawables()
                [DRAWABLE_RIGHT].getBounds().width()));
        //Log.e("raja", "clicked on delete img 1: compound drawable left: "+((TextView)v).getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width());

        //right drawable
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(event.getRawX() >= (v.getRight() -((TextView) v).getCompoundDrawables()
                    [DRAWABLE_RIGHT].getBounds().width())) {
                Log.e("raja", "clicked on delete img");

                return true;
            }
        }

        //for left drawable
        //if(event.getRawX() <= (editComment.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))
        return false;
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