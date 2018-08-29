package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import uk.co.victoriajanedavis.vimeo_api_test.ui.ListItem;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public class VimeoComment implements ListItem, Parcelable {

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("text") @Expose private String text;
    @SerializedName("created_on") @Expose private Date created_time;
    @SerializedName("user") @Expose private VimeoUser user;

    private long id = ListItem.ID_UNINITIALIZED;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        dest.writeString(this.text);
        dest.writeLong(this.created_time.getTime());
        dest.writeParcelable(this.user, flags);
    }

    protected VimeoComment(Parcel in) {
        this.uri = in.readString();
        this.text = in.readString();
        this.created_time = new Date(in.readLong());
        this.user = in.readParcelable(VimeoUser.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoComment> CREATOR = new Parcelable.Creator<VimeoComment>() {
        @Override
        public VimeoComment createFromParcel(Parcel source) {
            return new VimeoComment(source);
        }

        @Override
        public VimeoComment[] newArray(int size) {
            return new VimeoComment[size];
        }
    };
}
