package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VimeoMetadataComment implements Parcelable {

    private VimeoConnection replies;


    public VimeoMetadataComment(VimeoConnection replies) {
        this.replies = replies;
    }

    public VimeoConnection getRepliesConnection() {
        return replies;
    }

    public void setRepliesConnection(VimeoConnection users) {
        this.replies = replies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.replies, flags);
    }

    protected VimeoMetadataComment(Parcel in) {
        this.replies = in.readParcelable(VimeoConnection.class.getClassLoader());
    }

    public static final Creator<VimeoMetadataComment> CREATOR = new Creator<VimeoMetadataComment>() {
        @Override
        public VimeoMetadataComment createFromParcel(Parcel source) {
            return new VimeoMetadataComment(source);
        }

        @Override
        public VimeoMetadataComment[] newArray(int size) {
            return new VimeoMetadataComment[size];
        }
    };
}
