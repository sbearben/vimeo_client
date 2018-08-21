package uk.co.victoriajanedavis.vimeo_api_test.data.remote.vimeo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VimeoStats implements Parcelable {

    @SerializedName("plays") @Expose private Long plays;


    public Long getPlays() {
        return plays;
    }

    public void setPlays(long plays) {
        this.plays = plays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (plays != null) {
            dest.writeLong(this.plays);
        }
        else {
            dest.writeLong(0L);
        }
    }

    protected VimeoStats(Parcel in) {
        this.plays = in.readLong();
        if (plays == 0L) {
            this.plays = null;
        }
    }

    public static final Parcelable.Creator<VimeoStats> CREATOR = new Parcelable.Creator<VimeoStats>() {
        @Override
        public VimeoStats createFromParcel(Parcel source) {
            return new VimeoStats(source);
        }

        @Override
        public VimeoStats[] newArray(int size) {
            return new VimeoStats[size];
        }
    };
}
