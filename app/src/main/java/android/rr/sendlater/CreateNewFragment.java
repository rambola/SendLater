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
    private Button mCancelBtn;
    private Button mSaveMsgBtn;

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
        mCancelBtn = mIncludeLayout.findViewById(R.id.cancelBtn);
        mSaveMsgBtn = mIncludeLayout.findViewById(R.id.saveBtn);
        mCancelBtn.setOnClickListener(mCreateNewFragmentPresenter);
        mSaveMsgBtn.setOnClickListener(mCreateNewFragmentPresenter);
        mEnterMsgET.addTextChangedListener(mCreateNewFragmentPresenter);
    }

    @Override
    public void showCreateNewLayout() {
        mMsgTV.setVisibility(View.GONE);
    }

}