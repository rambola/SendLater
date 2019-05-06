package android.rr.sendlater.model;

public class ContactsModel {
    private String mName;
    private String mNumber;

    public ContactsModel (String name, String number) {
        mName = name;
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

}
