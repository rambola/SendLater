package android.rr.sendlater;

import android.content.Intent;
import android.rr.sendlater.adapter.MultiSelectionContactsListAdapter;
import android.rr.sendlater.model.ContactsList;
import android.rr.sendlater.presenter.MultiContactsPickerPresenter;
import android.rr.sendlater.utils.SendLaterConstants;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

public class MultiContactPickerActivity extends AppCompatActivity implements
        MultiContactsPickerPresenter.MultiContactsPickerView {

    private TextView mLoadProgressTV;
    private MultiSelectionContactsListAdapter mMultiSelectionContactsListAdapter;
    private MultiContactsPickerPresenter mMultiContactsPickerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_contact_picker);

        initViews();
    }

    private void initViews () {
        mLoadProgressTV = findViewById(R.id.txt_load_progress);
        RecyclerView recyclerView = findViewById(R.id.multiContactsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiSelectionContactsListAdapter = new MultiSelectionContactsListAdapter(
                new ContactsList());
        recyclerView.setAdapter(mMultiSelectionContactsListAdapter);

        mMultiContactsPickerPresenter = new MultiContactsPickerPresenter(
                MultiContactPickerActivity.this);
        mMultiContactsPickerPresenter.loadContacts(mMultiSelectionContactsListAdapter,
                mLoadProgressTV, "");

        EditText searchET = findViewById(R.id.txt_filter);
        searchET.addTextChangedListener(mMultiContactsPickerPresenter);

        findViewById(R.id.doneBtn).setOnClickListener(mMultiContactsPickerPresenter);
    }

    @Override
    public void sendSelectedContactsList(ContactsList contactsList) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(SendLaterConstants.MULTI_SELECT_CONTACTS_INTENT_KEY,
                contactsList.contactArrayList);
        setResult(SendLaterConstants.MULTI_SELECT_CONTACTS_RESULT_CODE, intent);
        finish();
    }

}