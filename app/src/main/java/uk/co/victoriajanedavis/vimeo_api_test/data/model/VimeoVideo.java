package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo.VimeoStats;
import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class VimeoVideo implements ListItem, Parcelable {

    // NOTE: to open a full screen video in a web browser the format is: https://player.vimeo.com/video/{id} (maybe implement this opening onclick in a WebView)

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("name") @Expose private String name;
    @SerializedName("description") @Expose private String description;
    @SerializedName("duration") @Expose private int duration_seconds;
    @SerializedName("created_time") @Expose private Date created_time;
    @SerializedName("user") @Expose private VimeoUser user;
    @SerializedName("pictures") @Expose private VimeoPictures pictures;
    @SerializedName("metadata") @Expose private VimeoMetadataVideo metadata;
    @SerializedName("stats") @Expose private VimeoStats stats;

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

    public int getDurationSeconds() {
        return duration_seconds;
    }

    public void setDurationSeconds(int duration_seconds) {
        this.duration_seconds = duration_seconds;
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

    public VimeoPictures getPictures() {
        return pictures;
    }

    public void setPictures(VimeoPictures pictures) {
        this.pictures = pictures;
    }

    public VimeoMetadataVideo getMetadata() {
        return metadata;
    }

    public void setMetadata(VimeoMetadataVideo metadata) {
        this.metadata = metadata;
    }

    public VimeoStats getStats() {
        return stats;
    }

    public void setStats (VimeoStats stats) {
        this.stats = stats;
    }

    @Override
    public long getId() {
        if (id == ListItem.ID_UNINITIALIZED || id == 0) {
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
        dest.writeInt(this.duration_seconds);
        dest.writeLong(this.created_time.getTime());
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.pictures, flags);
        dest.writeParcelable(this.metadata, flags);
        dest.writeParcelable(this.stats, flags);
        //dest.writeList(mUrls);
    }

    protected VimeoVideo(Parcel in) {
        this.uri = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.duration_seconds = in.readInt();
        this.created_time = new Date(in.readLong());
        this.user = in.readParcelable(VimeoUser.class.getClassLoader());
        this.pictures = in.readParcelable(VimeoPictures.class.getClassLoader());
        this.metadata = in.readParcelable(VimeoMetadataVideo.class.getClassLoader());
        this.stats = in.readParcelable(VimeoStats.class.getClassLoader());
        //mUrls = new ArrayList<>();
        //in.readList(mUrls, Url.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoVideo> CREATOR = new Parcelable.Creator<VimeoVideo>() {
        @Override
        public VimeoVideo createFromParcel(Parcel source) {
            return new VimeoVideo(source);
        }

        @Override
        public VimeoVideo[] newArray(int size) {
            return new VimeoVideo[size];
        }
    };
}
