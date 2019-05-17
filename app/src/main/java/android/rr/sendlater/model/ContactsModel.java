package android.rr.sendlater.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ContactsModel implements Parcelable {
    public String mPhoneId;
    public String mName;
    public String mNumber;
    private String mLabel;

    ContactsModel (String phoneId, String name, String number, String label) {
        mPhoneId = phoneId;
        mName = name;
        mNumber = number;
        mLabel = label;
    }

    private ContactsModel(Parcel in) {
        mPhoneId = in.readString();
        mName = in.readString();
        mNumber = in.readString();
        mLabel = in.readString();
    }

    public static final Creator<ContactsModel> CREATOR = new Creator<ContactsModel>() {
        @Override
        public ContactsModel createFromParcel(Parcel in) {
            return new ContactsModel(in);
        }

        @Override
        public ContactsModel[] newArray(int size) {
            return new ContactsModel[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return mName+" | "+mLabel+" : "+mNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoneId);
        dest.writeString(mName);
        dest.writeString(mNumber);
        dest.writeString(mLabel);
    }

}