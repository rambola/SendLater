package android.rr.sendlater.presenter;

import android.os.AsyncTask;
import android.rr.sendlater.MultiContactPickerActivity;
import android.rr.sendlater.R;
import android.rr.sendlater.adapter.MultiSelectionContactsListAdapter;
import android.rr.sendlater.model.ContactsList;
import android.rr.sendlater.model.ContactsLoader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class MultiContactsPickerPresenter implements TextWatcher, View.OnClickListener {

    private MultiContactPickerActivity mMultiContactPickerActivity;
    private ContactsLoader mContactsLoader;
    private MultiSelectionContactsListAdapter mMultiSelectionContactsListAdapter;

    public MultiContactsPickerPresenter (MultiContactPickerActivity multiContactPickerActivity) {
        mMultiContactPickerActivity = multiContactPickerActivity;
    }

    public void loadContacts(MultiSelectionContactsListAdapter multiSelectionContactsListAdapter,
                             TextView txtProgress, String filter) {
        mMultiSelectionContactsListAdapter = multiSelectionContactsListAdapter;
        if(mContactsLoader!=null && mContactsLoader.getStatus()!= AsyncTask.Status.FINISHED){
            try{
                mContactsLoader.cancel(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        try{
            //Running AsyncLoader with adapter and  filter
            mContactsLoader = new ContactsLoader(mMultiContactPickerActivity,
                    multiSelectionContactsListAdapter);
            mContactsLoader.mTxtProgress = txtProgress;
            mContactsLoader.execute(filter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mMultiSelectionContactsListAdapter.filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneBtn:
                mMultiContactPickerActivity.sendSelectedContactsList(
                        mMultiSelectionContactsListAdapter.getSelectedContactsList());
                break;
        }
    }

    public interface MultiContactsPickerView {
        void sendSelectedContactsList(ContactsList contactsList);
    }

}