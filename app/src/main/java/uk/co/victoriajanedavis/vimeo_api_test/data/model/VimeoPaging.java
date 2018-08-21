package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VimeoPaging implements Parcelable {

    @SerializedName("next") @Expose private String next_uri;
    @SerializedName("previous") @Expose private String previous_uri;
    @SerializedName("first") @Expose private String first_uri;
    @SerializedName("last") @Expose private String last_uri;

    public String getNextUri() {
        return next_uri;
    }

    public void setNextUri(String next_uri) {
        this.next_uri = next_uri;
    }

    public String getPreviousUri() {
        return previous_uri;
    }

    public void setPreviousUri(String previous_uri) {
        this.previous_uri = previous_uri;
    }

    public String getFirstUri() {
        return first_uri;
    }

    public void setFirstUri(String first_uri) {
        this.first_uri = first_uri;
    }

    public String getLastUri() {
        return last_uri;
    }

    public void setLastUri(String last_uri) {
        this.last_uri = last_uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.next_uri);
        dest.writeString(this.previous_uri);
        dest.writeString(this.first_uri);
        dest.writeString(this.last_uri);
    }

    protected VimeoPaging(Parcel in) {
        this.next_uri = in.readString();
        this.previous_uri = in.readString();
        this.first_uri = in.readString();
        this.last_uri = in.readString();
    }

    public static final Parcelable.Creator<VimeoPaging> CREATOR = new Parcelable.Creator<VimeoPaging>() {
        @Override
        public VimeoPaging createFromParcel(Parcel source) {
            return new VimeoPaging(source);
        }

        @Override
        public VimeoPaging[] newArray(int size) {
            return new VimeoPaging[size];
        }
    };
}
