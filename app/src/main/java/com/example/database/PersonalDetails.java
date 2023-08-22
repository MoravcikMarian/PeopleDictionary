package com.example.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PersonalDetails implements Parcelable {
    public int id;
    public String name = "";
    public String gender = "";
    public int age = 0;
    public String address = "";
    public String city = "";
    public int zip = 0;

    public PersonalDetails() {

    }
    protected PersonalDetails(Parcel in) {
        id = in.readInt();
        name = in.readString();
        gender = in.readString();
        age = in.readInt();
        address = in.readString();
        city = in.readString();
        zip = in.readInt();
    }

    public static final Creator<PersonalDetails> CREATOR = new Creator<PersonalDetails>() {
        @Override
        public PersonalDetails createFromParcel(Parcel in) {
            return new PersonalDetails(in);
        }

        @Override
        public PersonalDetails[] newArray(int size) {
            return new PersonalDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeInt(age);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeInt(zip);
    }
    public boolean contains(String text) {
        if(name.toLowerCase().contains(text)) return true;
        else if(gender.toLowerCase().contains(text)) return true;
        else if(String.valueOf(age).toLowerCase().contains(text)) return true;
        else if(address.toLowerCase().contains(text)) return true;
        else if(city.toLowerCase().contains(text)) return true;
        else return String.valueOf(zip).toLowerCase().contains(text);
    }

}
