package android.rr.sendlater;

import android.rr.sendlater.adapter.MultiSelectionContactsListAdapter;
import android.rr.sendlater.model.ContactsModel;
import android.rr.sendlater.presenter.MultiContactsPickerPresenter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MultiContactPickerActivity extends AppCompatActivity implements
        MultiContactsPickerPresenter.MultiContactsPickerView {

    private RecyclerView mRecyclerView;
    private MultiSelectionContactsListAdapter mMultiSelectionContactsListAdapter;
    private List<ContactsModel> mContactsModelList = new ArrayList<>();
    private MultiContactsPickerPresenter mMultiContactsPickerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_contact_picker);

        initViews();
    }

    private void initViews () {
        mMultiContactsPickerPresenter = new MultiContactsPickerPresenter(
                MultiContactPickerActivity.this);
        mRecyclerView = findViewById(R.id.multiContactsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MultiContactPickerActivity.this));
        mMultiSelectionContactsListAdapter = new MultiSelectionContactsListAdapter(
                MultiContactPickerActivity.this, mContactsModelList);
        mRecyclerView.setAdapter(mMultiSelectionContactsListAdapter);

        mMultiContactsPickerPresenter.loadContacts();
    }

    @Override
    public void onBackPressed() {
        setResult(111, null);
        super.onBackPressed();
    }

    @Override
    public void updateTheAdapter(List<ContactsModel> contactsModelList) {
        mContactsModelList = contactsModelList;
        mMultiSelectionContactsListAdapter.notifyDataSetChanged();
    }
}