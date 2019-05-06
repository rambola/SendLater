package android.rr.sendlater.adapter;

import android.content.Context;
import android.rr.sendlater.model.ContactsModel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class MultiSelectionContactsListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ContactsModel> mContactsModelList;

    public MultiSelectionContactsListAdapter (Context context, List<ContactsModel> contactsModels) {
        mContext = context;
        mContactsModelList = contactsModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mContactsModelList.size();
    }

}