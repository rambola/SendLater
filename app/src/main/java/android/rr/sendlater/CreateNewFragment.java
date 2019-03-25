package android.rr.sendlater;

import android.content.Context;
import android.os.Bundle;
import android.rr.sendlater.presenter.CreateNewFragmentPresenter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateNewFragment extends Fragment implements
        CreateNewFragmentPresenter.CreateNewFragmentView {
    private CreateNewFragmentPresenter mCreateNewFragmentPresenter;

    private TextView mMsgTV;
    private View mIncludeLayout;
    private LinearLayout mSelectedContactsLayout;
    private EditText mEnterMsgET;
    private TextView mChosenDateTV;
    private TextView mChosenTimeTV;
    private TextView mCharCountTV;

    public static CreateNewFragment newInstance() {
        return new CreateNewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateNewFragmentPresenter = new CreateNewFragmentPresenter(CreateNewFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        showCreateNewLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initViews (View view) {
        mMsgTV = view.findViewById(R.id.msgTV);
        mMsgTV.setOnClickListener(mCreateNewFragmentPresenter);

        mIncludeLayout = view.findViewById(R.id.createNewIncludeLayout);
        mSelectedContactsLayout = mIncludeLayout.findViewById(R.id.selectedContactsLayout);
        mEnterMsgET = mIncludeLayout.findViewById(R.id.enterMsgET);
        mCharCountTV = mIncludeLayout.findViewById(R.id.enteredCharContTV);
        mChosenDateTV = mIncludeLayout.findViewById(R.id.selectedDataTV);
        mChosenTimeTV = mIncludeLayout.findViewById(R.id.selectedTimeTV);

        mIncludeLayout.findViewById(R.id.cancelBtn).setOnClickListener(mCreateNewFragmentPresenter);
        mIncludeLayout.findViewById(R.id.saveBtn).setOnClickListener(mCreateNewFragmentPresenter);
        mEnterMsgET.addTextChangedListener(mCreateNewFragmentPresenter);
        mChosenDateTV.setOnClickListener(mCreateNewFragmentPresenter);
        mChosenTimeTV.setOnClickListener(mCreateNewFragmentPresenter);
    }

    @Override
    public void showCreateNewLayout() {
        mMsgTV.setVisibility(View.GONE);
        mIncludeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void msgCharCount(int charCount) {
        mCharCountTV.setText(charCount+"/160");
    }

    @Override
    public void selectedDate(int dayOfMonth, int month, int year) {
        month = month + 1;
        String mnth;
        if (month < 10)
            mnth = "0"+month;
        else
            mnth = ""+month;

        mChosenDateTV.setText(getString(R.string.chosenDate)+dayOfMonth+"/"+mnth+"/"+year);
    }

    @Override
    public void selectedTime(int hourOfDay, int minute) {
        mChosenTimeTV.setText(getString(R.string.chosenTime)+hourOfDay+":"+minute);
    }

    @Override
    public void resetViews() {
        mSelectedContactsLayout.removeAllViews();
        mEnterMsgET.setText("");
        mChosenDateTV.setText(getString(R.string.chosenDate));
        mChosenTimeTV.setText(getString(R.string.chosenTime));
        mCreateNewFragmentPresenter.showToast(getString(R.string.resettingTheViews));
    }

    @Override
    public void hideKeyBoard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}