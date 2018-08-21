package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VimeoMetadataUser implements Parcelable {

    private VimeoConnection followers;
    private VimeoConnection following;
    private VimeoConnection likes;
    private VimeoConnection videos;


    public VimeoMetadataUser(VimeoConnection followers, VimeoConnection following,
                             VimeoConnection likes, VimeoConnection videos) {
        this.followers = followers;
        this.following = following;
        this.likes = likes;
        this.videos = videos;
    }

    public VimeoConnection getFollowersConnection() {
        return followers;
    }

    public void setFollowersConnection(VimeoConnection followers) {
        this.followers = followers;
    }

    public VimeoConnection getFollowingConnection() {
        return following;
    }

    public void setFollowingConnection(VimeoConnection following) {
        this.following = following;
    }

    public VimeoConnection getLikesConnection() {
        return likes;
    }

    public void setLikesConnection(VimeoConnection likes) {
        this.likes = likes;
    }

    public VimeoConnection getVideosConnection() {
        return videos;
    }

    public void setVideosConnection(VimeoConnection videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.followers, flags);
        dest.writeParcelable(this.following, flags);
        dest.writeParcelable(this.likes, flags);
        dest.writeParcelable(this.videos, flags);
    }

    protected VimeoMetadataUser(Parcel in) {
        this.followers = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.following = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.likes = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.videos = in.readParcelable(VimeoConnection.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoMetadataUser> CREATOR = new Parcelable.Creator<VimeoMetadataUser>() {
        @Override
        public VimeoMetadataUser createFromParcel(Parcel source) {
            return new VimeoMetadataUser(source);
        }

        @Override
        public VimeoMetadataUser[] newArray(int size) {
            return new VimeoMetadataUser[size];
        }
    };
}
