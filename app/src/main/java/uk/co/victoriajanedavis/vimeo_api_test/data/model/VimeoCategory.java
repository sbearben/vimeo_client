package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public class VimeoCategory implements ListItem, Parcelable {

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("name") @Expose private String name;
    @SerializedName("pictures") @Expose private VimeoPictures pictures;
    @SerializedName("icon") @Expose private VimeoPictures icons;

    private long id = ListItem.ID_UNINITIALIZED;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VimeoPictures getPictures() {
        return pictures;
    }

    public void setPictures(VimeoPictures pictures) {
        this.pictures = pictures;
    }

    public VimeoPictures getIcons() {
        return icons;
    }

    public void setIcons(VimeoPictures icons) {
        this.icons = icons;
    }

    @Override
    public long getId() {
        if (id == ListItem.ID_UNINITIALIZED) {
            id = VimeoTextUtil.generateIdFromUri(getUri());
        }
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof VimeoCategory))return false;
        return this.getId() == ((VimeoCategory) other).getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeString(this.name);
        dest.writeParcelable(this.pictures, flags);
        dest.writeParcelable(this.icons, flags);
    }

    protected VimeoCategory(Parcel in) {
        this.uri = in.readString();
        this.name = in.readString();
        this.pictures = in.readParcelable(VimeoPictures.class.getClassLoader());
        this.icons = in.readParcelable(VimeoPictures.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoCategory> CREATOR = new Parcelable.Creator<VimeoCategory>() {
        @Override
        public VimeoCategory createFromParcel(Parcel source) {
            return new VimeoCategory(source);
        }

        @Override
        public VimeoCategory[] newArray(int size) {
            return new VimeoCategory[size];
        }
    };
}
