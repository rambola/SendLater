package android.rr.sendlater.model;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.rr.sendlater.presenter.CreateNewFragmentPresenter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

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

    public interface CreateNewFragModel {
        void chosenDate (int dayOfMonth, int month, int year);
        void chosenTime (int hourOfDay, int minute);
        void selectedInvalidTime();
    }
}