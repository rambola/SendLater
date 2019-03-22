package android.rr.sendlater.presenter;

import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.R;
import android.view.View;

public class CreateNewFragmentPresenter implements View.OnClickListener {
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
        }
    }

    public interface CreateNewFragmentView {
        void showCreateNewLayout();
    }
}