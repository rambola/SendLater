package android.rr.sendlater.presenter;

import android.rr.sendlater.MultiContactPickerActivity;
import android.rr.sendlater.model.ContactsModel;

import java.util.List;

public class MultiContactsPickerPresenter {

    private MultiContactPickerActivity mMultiContactPickerActivity;

    public MultiContactsPickerPresenter (MultiContactPickerActivity multiContactPickerActivity) {
        mMultiContactPickerActivity = multiContactPickerActivity;
    }

    public void loadContacts() {

    }

    public interface MultiContactsPickerView {
        void updateTheAdapter(List<ContactsModel> contactsModelList);
    }

}