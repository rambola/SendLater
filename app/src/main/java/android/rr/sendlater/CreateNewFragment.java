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
import android.widget.TextView;

public class CreateNewFragment extends Fragment implements
        CreateNewFragmentPresenter.CreateNewFragmentView {
    private CreateNewFragmentPresenter mCreateNewFragmentPresenter;
    private TextView mMsgTV;

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

        mMsgTV = view.findViewById(R.id.msgTV);
        mMsgTV.setOnClickListener(mCreateNewFragmentPresenter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).changeActionBarTitle(getString(R.string.createNew));
    }


    @Override
    public void showCreateNewLayout() {
        mMsgTV.setVisibility(View.GONE);
    }

}