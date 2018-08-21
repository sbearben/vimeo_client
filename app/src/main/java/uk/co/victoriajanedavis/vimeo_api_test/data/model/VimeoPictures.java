package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VimeoPictures implements Parcelable {

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("sizes") @Expose private List<VimeoPictureSizes> sizesList;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<VimeoPictureSizes> getSizesList() {
        return sizesList;
    }

    public void setSizesList(List<VimeoPictureSizes> sizesList) {
        this.sizesList = sizesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeList(this.sizesList);
        //dest.writeList(mUrls);
    }

    protected VimeoPictures(Parcel in) {
        this.uri = in.readString();
        this.sizesList = new ArrayList<>();
        in.readList(this.sizesList, VimeoPictureSizes.class.getClassLoader());
        //this.sizesList = in.readArrayList(VimeoPictureSizes.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoPictures> CREATOR = new Parcelable.Creator<VimeoPictures>() {
        @Override
        public VimeoPictures createFromParcel(Parcel source) {
            return new VimeoPictures(source);
        }

        @Override
        public VimeoPictures[] newArray(int size) {
            return new VimeoPictures[size];
        }
    };
}
