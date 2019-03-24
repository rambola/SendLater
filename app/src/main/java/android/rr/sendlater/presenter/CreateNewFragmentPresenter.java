package android.rr.sendlater.presenter;

import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class CreateNewFragmentPresenter implements View.OnClickListener, TextWatcher {
    private CreateNewFragment mCreateNewFragment;

    public CreateNewFragmentPresenter (CreateNewFragment createNewFragment) {
        mCreateNewFragment = createNewFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgTV:
                mCreateNewFragment.showCreateNewLayout();
                break;
            case R.id.cancelBtn:
                break;
            case R.id.saveBtn:
                break;
            case R.id.selectedDataTV:
                break;
            case R.id.selectedTimeTV:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface CreateNewFragmentView {
        void showCreateNewLayout();
    }
}