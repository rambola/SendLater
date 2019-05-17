package android.rr.sendlater.adapter;

import android.rr.sendlater.R;
import android.rr.sendlater.model.ContactsList;
import android.rr.sendlater.model.ContactsModel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

public class MultiSelectionContactsListAdapter extends
        RecyclerView.Adapter<MultiSelectionContactsListAdapter.MyViewHolder> {

    private ContactsList mContactsList;
    private ContactsList mFilteredContactsList;
    private ContactsList mSelectedContactsList;
    private String mFilterContactName="";

    public MultiSelectionContactsListAdapter (ContactsList contactsList) {
        mContactsList = contactsList;
        mSelectedContactsList = new ContactsList();
        mFilteredContactsList = new ContactsList();
    }

    public void filter(String filterContactName){
        mFilteredContactsList.contactArrayList.clear();

        if(filterContactName.isEmpty() || filterContactName.length()<1) {
            mFilteredContactsList.contactArrayList.addAll(mContactsList.contactArrayList);
            mFilterContactName = "";
        } else {
            mFilterContactName = filterContactName.toLowerCase().trim();
            for (int i = 0; i < mContactsList.contactArrayList.size(); i++) {

                if (mContactsList.contactArrayList.get(i).mName.toLowerCase().
                        contains(filterContactName))
                    mFilteredContactsList.addContact(mContactsList.contactArrayList.get(i));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem= LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.contacts_list_item, viewGroup, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder mMyViewHolder, int position) {
        mMyViewHolder.chkContact.setText(
                mFilteredContactsList.contactArrayList.get(position).toString());
        mMyViewHolder.chkContact.setId(Integer.parseInt(
                mFilteredContactsList.contactArrayList.get(position).mPhoneId));
        mMyViewHolder.chkContact.setChecked(alreadySelected(
                mFilteredContactsList.contactArrayList.get(position)));

        mMyViewHolder.chkContact.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContactsModel contact = mFilteredContactsList.getContact(buttonView.getId());

                if(null != contact && isChecked && !alreadySelected(contact)) {
                    mSelectedContactsList.addContact(contact);
                } else if(null != contact && !isChecked) {
                    mSelectedContactsList.removeContact(contact);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredContactsList.contactArrayList.size();
    }

    private boolean alreadySelected(ContactsModel contact)
    {
        return mSelectedContactsList.getContact(Integer.parseInt(contact.mPhoneId)) != null;
    }

    public void addContacts (List<ContactsModel> tempContactHolder) {
        mContactsList.contactArrayList.addAll(tempContactHolder);
        filter(mFilterContactName);
    }

    public ContactsList getSelectedContactsList () {
        return mSelectedContactsList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox chkContact;
        MyViewHolder(View itemView) {
            super(itemView);
            chkContact = itemView.findViewById(R.id.chk_contact);
        }
    }

}