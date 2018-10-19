package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import uk.co.victoriajanedavis.vimeo_api_test.ui.base.list.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.ui.base.follow.ListItemFollowInteractor;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoTextUtil;

public class VimeoUser implements Parcelable, ListItemFollowInteractor {

    public static final String ACCOUNT_TYPE_BASIC = "basic";
    public static final String ACCOUNT_TYPE_PLUS = "plus";
    public static final String ACCOUNT_TYPE_PRODUCER = "producer";
    public static final String ACCOUNT_TYPE_PRO = "pro";
    public static final String ACCOUNT_TYPE_BUSINESS = "business";
    public static final String ACCOUNT_TYPE_PREMIUM = "premium";

    @StringDef({ACCOUNT_TYPE_BASIC, ACCOUNT_TYPE_PLUS, ACCOUNT_TYPE_PRODUCER, ACCOUNT_TYPE_PRO,
            ACCOUNT_TYPE_BUSINESS, ACCOUNT_TYPE_PREMIUM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AccountType {
    }

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("name") @Expose private String name;
    @SerializedName("location") @Expose private String location;
    @SerializedName("bio") @Expose private String bio;
    @SerializedName("account") @Expose private String account;
    @SerializedName("pictures") @Expose private VimeoPictures pictures;
    @SerializedName("metadata") @Expose private VimeoMetadataUser metadata;
    private VimeoCollection<VimeoVideo> videosCollection;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(@AccountType String account) {
        this.account = account;
    }

    public VimeoPictures getPictures() {
        return pictures;
    }

    public void setPictures(VimeoPictures pictures) {
        this.pictures = pictures;
    }

    public VimeoMetadataUser getMetadata() {
        return metadata;
    }

    public void setMetadata(VimeoMetadataUser metadata) {
        this.metadata = metadata;
    }

    public VimeoCollection<VimeoVideo> getVideosCollection() {
        return videosCollection;
    }

    public void setVideosCollection(VimeoCollection<VimeoVideo> videosCollection) {
        this.videosCollection = videosCollection;
    }

    @Override
    public long getId() {
        if (id == ListItem.ID_UNINITIALIZED || id == 0) {
            id = VimeoTextUtil.generateIdFromUri(getUri());
        }
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof VimeoUser))return false;
        return this.getId() == ((VimeoUser) other).getId();
    }

    @Override
    public VimeoInteraction getFollowInteraction() {
        return metadata.getFollowInteraction();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeString(this.bio);
        dest.writeString(this.account);
        dest.writeParcelable(this.pictures, flags);
        dest.writeParcelable(this.metadata, flags);
        dest.writeParcelable(this.videosCollection, flags);
    }

    protected VimeoUser(Parcel in) {
        this.uri = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.bio = in.readString();
        this.account = in.readString();
        this.pictures = in.readParcelable(VimeoPictures.class.getClassLoader());
        this.metadata = in.readParcelable(VimeoMetadataUser.class.getClassLoader());
        this.videosCollection = in.readParcelable(VimeoCollection.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoUser> CREATOR = new Parcelable.Creator<VimeoUser>() {
        @Override
        public VimeoUser createFromParcel(Parcel source) {
            return new VimeoUser(source);
        }

        @Override
        public VimeoUser[] newArray(int size) {
            return new VimeoUser[size];
        }
    };
}
