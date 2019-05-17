package android.rr.sendlater.model;

import java.io.Serializable;
import java.util.ArrayList;


public class ContactsList implements Serializable {

    public ArrayList<ContactsModel> contactArrayList;

    public ContactsList() {
        contactArrayList = new ArrayList<>();
    }

    public int getCount() {
        return contactArrayList.size();
    }

    public void addContact(ContactsModel contact) {
        contactArrayList.add(contact);
    }

    public void removeContact(ContactsModel contact) {
        contactArrayList.remove(contact);
    }

    public ContactsModel getContact(int id) {
        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).mPhoneId) == id)
                return contactArrayList.get(i);
        }

        return null;
    }

}