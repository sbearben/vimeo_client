package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VimeoPictureSizes implements Parcelable {

    @SerializedName("width") @Expose private int width;
    @SerializedName("height") @Expose private int height;
    @SerializedName("link") @Expose private String link;
    @SerializedName("link_with_play_button") @Expose private String link_with_play_button;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkWithPlayButton() {
        return link_with_play_button;
    }

    public void setLinkWithPlayButton(String link_with_play_button) {
        this.link_with_play_button = link_with_play_button;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.link);
        dest.writeString(this.link_with_play_button);
    }

    protected VimeoPictureSizes(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
        this.link = in.readString();
        this.link_with_play_button = in.readString();
    }

    public static final Parcelable.Creator<VimeoPictureSizes> CREATOR = new Parcelable.Creator<VimeoPictureSizes>() {
        @Override
        public VimeoPictureSizes createFromParcel(Parcel source) {
            return new VimeoPictureSizes(source);
        }

        @Override
        public VimeoPictureSizes[] newArray(int size) {
            return new VimeoPictureSizes[size];
        }
    };
}
