package android.rr.sendlater.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.rr.sendlater.R;
import android.rr.sendlater.adapter.MultiSelectionContactsListAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContactsLoader extends AsyncTask<String,Void,Void> {

    private Context mContext;
    private List<ContactsModel> mTempContactHolder;
    public TextView mTxtProgress;
    private int mTotalContactsCount;
    private int mLoadedContactsCount;
    private MultiSelectionContactsListAdapter mMultiSelectionContactsListAdapter;

    public ContactsLoader(Context context,
                   MultiSelectionContactsListAdapter multiSelectionContactsListAdapter){
        mContext = context;
        mMultiSelectionContactsListAdapter = multiSelectionContactsListAdapter;
        mTempContactHolder = new ArrayList<>();
        mLoadedContactsCount = 0;
        mTotalContactsCount = 0;
        mTxtProgress=null;
    }

    @Override
    protected Void doInBackground(String[] filters) {
        String filter = filters[0];

        ContentResolver contentResolver = mContext.getContentResolver();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        Cursor cursor;
        if(filter.length()>0) {
            cursor = contentResolver.query(
                    uri,
                    projection,
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[]{filter},
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );
        }else {
            cursor = contentResolver.query(
                    uri,
                    projection,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );
        }

        mTotalContactsCount = cursor.getCount();
        if(null != cursor && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    String id = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id},
                            null);

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {
                        while (phoneCursor.moveToNext()) {
                            String phId = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone._ID));
                            String customLabel = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.LABEL));
                            String label = (String) ContactsContract.CommonDataKinds.Phone.
                                    getTypeLabel(mContext.getResources(),
                                    phoneCursor.getInt(phoneCursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.TYPE)),
                                    customLabel);
                            String phNo = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            mTempContactHolder.add(new ContactsModel(phId, name, phNo, label));
                        }

                        phoneCursor.close();
                    }
                }

                mLoadedContactsCount++;
                publishProgress();
            }

            cursor.close();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void[] v) {
        if(mTempContactHolder.size() >= 100) {
            mMultiSelectionContactsListAdapter.addContacts(mTempContactHolder);
            mTempContactHolder.clear();
            if(null != mTxtProgress) {
                mTxtProgress.setVisibility(View.VISIBLE);
//                String progressMessage = "Loading...("+mLoadedContactsCount+"/"+mTotalContactsCount+")";
                String progressMessage = mContext.getResources().getString(R.string.loadingContacts,
                        mLoadedContactsCount, mTotalContactsCount);
                mTxtProgress.setText(progressMessage);
            }
        }
    }

    @Override
    protected void onPostExecute(Void v) {
        mMultiSelectionContactsListAdapter.addContacts(mTempContactHolder);
        mTempContactHolder.clear();
        if(null != mTxtProgress) {
            mTxtProgress.setText("");
            mTxtProgress.setVisibility(View.GONE);
        }
    }

}