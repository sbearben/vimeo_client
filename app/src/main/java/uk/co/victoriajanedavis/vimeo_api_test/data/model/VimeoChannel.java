package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ParcelableListItem;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class VimeoChannel implements ParcelableListItem {

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("name") @Expose private String name;
    @SerializedName("description") @Expose private String description;
    @SerializedName("link") @Expose private String link;
    @SerializedName("created_time") @Expose private Date created_time;
    @SerializedName("user") @Expose private VimeoUser user;
    @SerializedName("header") @Expose private VimeoPictures header;
    @SerializedName("metadata") @Expose private VimeoMetadataChannel metadata;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(Date created_time) {
        this.created_time = created_time;
    }

    public VimeoUser getUser() {
        return user;
    }

    public void setUser(VimeoUser user) {
        this.user = user;
    }

    public VimeoPictures getHeader() {
        return header;
    }

    public void setHeader(VimeoPictures header) {
        this.header = header;
    }

    public VimeoMetadataChannel getMetadata() {
        return metadata;
    }

    public void setMetadata(VimeoMetadataChannel metadata) {
        this.metadata = metadata;
    }

    @Override
    public long getId() {
        if (id == ListItem.ID_UNINITIALIZED) {
            id = VimeoApiServiceUtil.generateIdFromUri(getUri());
        }
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.link);
        dest.writeLong(this.created_time.getTime());
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.header, flags);
        dest.writeParcelable(this.metadata, flags);
        //dest.writeList(mUrls);
    }

    protected VimeoChannel(Parcel in) {
        this.uri = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.created_time = new Date(in.readLong());
        this.user = in.readParcelable(VimeoUser.class.getClassLoader());
        this.header = in.readParcelable(VimeoPictures.class.getClassLoader());
        this.metadata = in.readParcelable(VimeoMetadataVideo.class.getClassLoader());
        //mUrls = new ArrayList<>();
        //in.readList(mUrls, Url.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoChannel> CREATOR = new Parcelable.Creator<VimeoChannel>() {
        @Override
        public VimeoChannel createFromParcel(Parcel source) {
            return new VimeoChannel(source);
        }

        @Override
        public VimeoChannel[] newArray(int size) {
            return new VimeoChannel[size];
        }
    };
}
