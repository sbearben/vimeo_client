package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VimeoMetadataVideo implements Parcelable {

    private VimeoConnection comments;
    private VimeoConnection likes;
    private VimeoConnection recommendations;


    public VimeoMetadataVideo (VimeoConnection comments, VimeoConnection likes, VimeoConnection recommendations) {
        this.comments = comments;
        this.likes = likes;
        this.recommendations = recommendations;
    }

    public VimeoConnection getCommentsConnection() {
        return comments;
    }

    public void setCommentsConnection(VimeoConnection comments) {
        this.comments = comments;
    }

    public VimeoConnection getLikesConnection() {
        return likes;
    }

    public void setLikesConnection(VimeoConnection likes) {
        this.likes = likes;
    }

    public VimeoConnection getRecommendationsConnection() {
        return recommendations;
    }

    public void setRecommendationsConnection(VimeoConnection recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.comments, flags);
        dest.writeParcelable(this.likes, flags);
        dest.writeParcelable(this.recommendations, flags);
    }

    protected VimeoMetadataVideo(Parcel in) {
        this.comments = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.likes = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.recommendations = in.readParcelable(VimeoConnection.class.getClassLoader());
    }

    public static final Parcelable.Creator<VimeoMetadataVideo> CREATOR = new Parcelable.Creator<VimeoMetadataVideo>() {
        @Override
        public VimeoMetadataVideo createFromParcel(Parcel source) {
            return new VimeoMetadataVideo(source);
        }

        @Override
        public VimeoMetadataVideo[] newArray(int size) {
            return new VimeoMetadataVideo[size];
        }
    };
}
