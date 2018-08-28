package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VimeoMetadataVideo implements Parcelable {

    private VimeoConnection comments;
    private VimeoConnection likes;
    private VimeoConnection recommendations;
    private VimeoInteraction watch_later;
    private VimeoInteraction like;


    public VimeoMetadataVideo (VimeoConnection comments, VimeoConnection likes, VimeoConnection recommendations,
                               VimeoInteraction watch_later, VimeoInteraction like) {
        this.comments = comments;
        this.likes = likes;
        this.recommendations = recommendations;
        this.watch_later = watch_later;
        this.like = like;
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

    public VimeoInteraction getWatchLaterInteraction() {
        return watch_later;
    }

    public void setWatchLaterInteraction(VimeoInteraction watch_later) {
        this.watch_later = watch_later;
    }

    public VimeoInteraction getLikeInteraction() {
        return like;
    }

    public void setLikeInteraction(VimeoInteraction like) {
        this.like = like;
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
        dest.writeParcelable(this.watch_later, flags);
        dest.writeParcelable(this.like, flags);
    }

    protected VimeoMetadataVideo(Parcel in) {
        this.comments = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.likes = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.recommendations = in.readParcelable(VimeoConnection.class.getClassLoader());
        this.watch_later = in.readParcelable(VimeoInteraction.class.getClassLoader());
        this.like = in.readParcelable(VimeoInteraction.class.getClassLoader());
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
