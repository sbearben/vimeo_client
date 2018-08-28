package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VimeoMetadataChannel implements Parcelable {

    private VimeoConnection users;
    private VimeoConnection videos;
    private VimeoInteraction follow;


    public VimeoMetadataChannel(VimeoConnection users, VimeoConnection videos, VimeoInteraction follow) {
        this.users = users;
        this.videos = videos;
        this.follow = follow;
    }

    public VimeoConnection getUsersConnection() {
        return users;
    }

    public void setUsersConnection(VimeoConnection users) {
        this.users = users;
    }

    public VimeoConnection getVideosConnection() {
        return videos;
    }

    public void setVideosConnection(VimeoConnection videos) {
        this.videos = videos;
    }

    public VimeoInteraction getFollowInteraction() {
        return follow;
    }

    public void setFollowInteraction(VimeoInteraction follow) {
        this.follow = follow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.users, flags);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.follow, flags);
    }

    protected VimeoMetadataChannel(Parcel in) {
        this.users = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.videos = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.follow = in.readParcelable(VimeoInteraction.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoMetadataChannel> CREATOR = new Parcelable.Creator<VimeoMetadataChannel>() {
        @Override
        public VimeoMetadataChannel createFromParcel(Parcel source) {
            return new VimeoMetadataChannel(source);
        }

        @Override
        public VimeoMetadataChannel[] newArray(int size) {
            return new VimeoMetadataChannel[size];
        }
    };
}
