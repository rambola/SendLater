package android.rr.sendlater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.rr.sendlater.model.ContactsModel;
import android.rr.sendlater.presenter.CreateNewFragmentPresenter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void selectedDate(int dayOfMonth, String mnth, int year) {
        mChosenDateTV.setText(getString(R.string.chosenDateWithPlaceHolders,
                dayOfMonth, mnth, year));
    }

    @Override
    public void selectedTime(int hourOfDay, String min) {
        mChosenTimeTV.setText(getString(R.string.chosenTimeWithPlaceHolders, hourOfDay, min));
    }

    @Override
    public void resetViews() {
        mCreateNewFragmentPresenter.showToast(getString(R.string.resettingTheViews));
        mEnterMsgET.setText("");
        mChosenDateTV.setText(getString(R.string.chosenDate));
        mChosenTimeTV.setText(getString(R.string.chosenTime));
        updateContactLayout();
    }

    @Override
    public void selectedContactsToLayout(List<TextView> textViewsList,
                                         LinearLayout.LayoutParams layoutParams) {
        if (mFlexboxLayout.getVisibility() == View.VISIBLE && textViewsList.size() > 0)
            mFlexboxLayout.removeAllViews();

        for (int i=0; i<textViewsList.size(); i++) {
            mFlexboxLayout.addView(textViewsList.get(i), layoutParams);
        }
    }

    @Override
    public void deleteContact(int position, int textViewListSize) {
        mFlexboxLayout.removeViewAt(position);
        if (textViewListSize < 1)
            updateContactLayout();
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

            mCreateNewFragmentPresenter.addSelectedContactsToLayout(contactsList);
        }
    }

    private void updateContactLayout () {
        mFlexboxLayout.removeAllViews();
        mFlexboxLayout.setVisibility(View.GONE);
        mSelectContactsTV.setVisibility(View.VISIBLE);
        mOrTV.setVisibility(View.VISIBLE);
        mEnterNumberET.setVisibility(View.VISIBLE);
    }

}