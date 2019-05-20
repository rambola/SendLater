package android.rr.sendlater.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.rr.sendlater.model.ContactsModel;
import android.rr.sendlater.model.CreateNewFragmentModel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateNewFragmentPresenter implements View.OnClickListener, TextWatcher,
        CreateNewFragmentModel.CreateNewFragModel, View.OnTouchListener {
    private CreateNewFragment mCreateNewFragment;
    private CreateNewFragmentModel mCreateNewFragmentModel;
    private List<TextView> mTextViewsList;

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

    private ShapeDrawable getTextViewBackgroundDrawable () {
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
        try {
            // Check if no view has focus:
            View view = mCreateNewFragment.getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) mCreateNewFragment.getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
        month = month + 1;
        String mnth;
        if (month < 10)
            mnth = "0"+month;
        else
            mnth = ""+month;
        mCreateNewFragment.selectedDate(dayOfMonth, mnth, year);
    }

    @Override
    public void chosenTime(int hourOfDay, int minute) {
        String min;
        if (minute < 10)
            min = "0"+minute;
        else
            min = ""+minute;
        mCreateNewFragment.selectedTime(hourOfDay, min);
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

    public void addSelectedContactsToLayout (ArrayList<ContactsModel> contactsModels) {
        int mSelectedContactsCount = contactsModels.size();
        mTextViewsList = new ArrayList<>();

        for (int i=0; i<mSelectedContactsCount; i++) {
            TextView textView = new TextView(mCreateNewFragment.getActivity());
            textView.setId(Integer.parseInt(contactsModels.get(i).mPhoneId));
            textView.setText(mCreateNewFragment.getActivity().
                    getString(R.string.selectedContacts, contactsModels.get(i).mName,
                    contactsModels.get(i).mNumber));
            textView.setTextSize(18f);
            textView.setPadding(22, 0, 0, 0);
            textView.setBackground(getTextViewBackgroundDrawable());
            textView.setTextColor(mCreateNewFragment.getActivity().
                    getResources().getColor(R.color.colorPrimary));
            textView.setCompoundDrawablesWithIntrinsicBounds(0,
                    0, android.R.drawable.ic_menu_delete, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setOnTouchListener(this);

            mTextViewsList.add(textView);
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(7, 7, 7, 7);

        mCreateNewFragment.selectedContactsToLayout(mTextViewsList, layoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //final int DRAWABLE_LEFT = 0;
        //final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        //final int DRAWABLE_BOTTOM = 3;

        //for left drawable
        //if(event.getRawX() <= (editComment.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))

        //right drawable
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(event.getRawX() >= (v.getRight() -((TextView) v).getCompoundDrawables()
                    [DRAWABLE_RIGHT].getBounds().width())) {
                for (int i=0; i<mTextViewsList.size(); i++) {
                    if (v.getId() == mTextViewsList.get(i).getId()) {
                        mTextViewsList.remove(i);
                        mCreateNewFragment.deleteContact (i, mTextViewsList.size());
                        break;
                    }
                }
                return true;
            }
        }

        return false;
    }

    public interface CreateNewFragmentView {
        void showCreateNewLayout();
        void msgCharCount(int charCount);
        void selectedDate(int dayOfMonth, String month, int year);
        void selectedTime(int hourOfDay, String minute);
        void resetViews();
        void selectedContactsToLayout(List<TextView> textViewsList,
                                      LinearLayout.LayoutParams layoutParams);
        void deleteContact(int position, int textViewsListSize);
    }

}