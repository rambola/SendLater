package android.rr.sendlater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.rr.sendlater.model.ContactsModel;
import android.rr.sendlater.presenter.CreateNewFragmentPresenter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class CreateNewFragment extends Fragment implements
        CreateNewFragmentPresenter.CreateNewFragmentView {
    private CreateNewFragmentPresenter mCreateNewFragmentPresenter;

    private TextView mMsgTV;
    private TextView mOrTV;
    private View mIncludeLayout;
    private FlexboxLayout mFlexboxLayout;
    private EditText mEnterMsgET;
    private EditText mEnterNumberET;
    private TextView mChosenDateTV;
    private TextView mChosenTimeTV;
    private TextView mCharCountTV;
    private TextView mSelectContactsTV;

    public static CreateNewFragment newInstance() {
        return new CreateNewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateNewFragmentPresenter = new CreateNewFragmentPresenter(CreateNewFragment.this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        mSelectContactsTV = mIncludeLayout.findViewById(R.id.selectContactsTV);
        mOrTV = mIncludeLayout.findViewById(R.id.selectContactOrOptionTV);
        mEnterNumberET = mIncludeLayout.findViewById(R.id.enterNumberET);
        mFlexboxLayout = mIncludeLayout.findViewById(R.id.selectedContactsLayout);

        mEnterMsgET = mIncludeLayout.findViewById(R.id.enterMsgET);
        mCharCountTV = mIncludeLayout.findViewById(R.id.enteredCharContTV);
        mChosenDateTV = mIncludeLayout.findViewById(R.id.selectedDataTV);
        mChosenTimeTV = mIncludeLayout.findViewById(R.id.selectedTimeTV);

        mIncludeLayout.findViewById(R.id.cancelBtn).setOnClickListener(mCreateNewFragmentPresenter);
        mIncludeLayout.findViewById(R.id.saveBtn).setOnClickListener(mCreateNewFragmentPresenter);
        mEnterMsgET.addTextChangedListener(mCreateNewFragmentPresenter);
        mChosenDateTV.setOnClickListener(mCreateNewFragmentPresenter);
        mChosenTimeTV.setOnClickListener(mCreateNewFragmentPresenter);
        mSelectContactsTV.setOnClickListener(mCreateNewFragmentPresenter);
        mCharCountTV.setText(getString(R.string.charCount, 0));
    }

    @Override
    public void showCreateNewLayout() {
        mMsgTV.setVisibility(View.GONE);
        mIncludeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void msgCharCount(int charCount) {
        mCharCountTV.setText(getString(R.string.charCount, charCount));
    }

    @Override
    public void selectedDate(int dayOfMonth, int month, int year) {
        month = month + 1;
        String mnth;
        if (month < 10)
            mnth = "0"+month;
        else
            mnth = ""+month;

        mChosenDateTV.setText(getString(R.string.chosenDateWithPlaceHolders,
                dayOfMonth, mnth, year));
    }

    @Override
    public void selectedTime(int hourOfDay, int minute) {
        String min;
        if (minute < 10)
            min = "0"+minute;
        else
            min = ""+minute;
        mChosenTimeTV.setText(getString(R.string.chosenTimeWithPlaceHolders, hourOfDay, min));
    }

    @Override
    public void resetViews() {
        mCreateNewFragmentPresenter.showToast(getString(R.string.resettingTheViews));
        mFlexboxLayout.removeAllViews();
        mEnterMsgET.setText("");
        mChosenDateTV.setText(getString(R.string.chosenDate));
        mChosenTimeTV.setText(getString(R.string.chosenTime));
        mFlexboxLayout.setVisibility(View.GONE);
        mSelectContactsTV.setVisibility(View.VISIBLE);
        mOrTV.setVisibility(View.VISIBLE);
        mEnterNumberET.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyBoard() {
        try {
            // Check if no view has focus:
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<ContactsModel> contactsList = data.
                getParcelableArrayListExtra("selectedContactsList");

        if (requestCode == 110 && resultCode == 111 && contactsList.size() > 0) {
            mSelectContactsTV.setVisibility(View.GONE);
            mOrTV.setVisibility(View.GONE);
            mEnterNumberET.setVisibility(View.GONE);
            mFlexboxLayout.setVisibility(View.VISIBLE);

            addSelectedContactsToLayout(contactsList);
        }
    }

    private void addSelectedContactsToLayout (ArrayList<ContactsModel> contactsModels) {
        int mSelectedContactsCount = contactsModels.size();
        List<TextView> mTextViewsList = new ArrayList<>();

        for (int i=0; i<mSelectedContactsCount; i++) {
            TextView textView = new TextView(getActivity());
            textView.setId(Integer.parseInt(contactsModels.get(i).mPhoneId));
            textView.setText(getString(R.string.selectedContacts, contactsModels.get(i).mName,
                    contactsModels.get(i).mNumber));
            textView.setTextSize(18f);
            textView.setPadding(22, 0, 0, 0);
            textView.setBackground(mCreateNewFragmentPresenter.getTextViewBackgroundDrawable());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setCompoundDrawablesWithIntrinsicBounds(0,
                    0, android.R.drawable.ic_menu_delete, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setOnTouchListener(mCreateNewFragmentPresenter);

            mTextViewsList.add(textView);
        }

        if (mFlexboxLayout.getVisibility() == View.VISIBLE && mSelectedContactsCount > 0)
            mFlexboxLayout.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(7, 7, 7, 7);

        for (int i=0; i<mTextViewsList.size(); i++) {
            mFlexboxLayout.addView(mTextViewsList.get(i), layoutParams);
        }
    }

}