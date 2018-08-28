package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VimeoInteraction implements Parcelable {

    public static final String OPTION_GET = "GET";
    public static final String OPTION_PUT = "PUT";
    public static final String OPTION_POST = "POST";
    public static final String OPTION_DELETE = "DELETE";

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("options") @Expose private List<String> optionsList;
    @SerializedName("added") @Expose private boolean added;
    @SerializedName("added_time") private @Expose Date added_time;
    @SerializedName("type") @Expose private String type;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getOptions() {
        return optionsList;
    }

    public void setOptions(List<String> optionsList) {
        this.optionsList = optionsList;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public Date getAddedTime() {
        return added_time;
    }

    public void setAddedTime(Date added_time) {
        this.added_time = added_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeList(this.optionsList);
        dest.writeInt(this.added ? 1 : 0);
        dest.writeLong(this.added_time != null ? added_time.getTime() : -1);
        dest.writeString(this.type);
    }

    protected VimeoInteraction(Parcel in) {
        this.uri = in.readString();
        this.optionsList = new ArrayList<>();
        in.readList(this.optionsList, String.class.getClassLoader());
        this.added = in.readInt() == 1;
        long tmpDate = in.readLong();
        this.added_time = tmpDate == -1 ? null : new Date(tmpDate);
        this.type = in.readString();
    }

    public static final Creator<VimeoInteraction> CREATOR = new Creator<VimeoInteraction>() {
        @Override
        public VimeoInteraction createFromParcel(Parcel source) {
            return new VimeoInteraction(source);
        }

        @Override
        public VimeoInteraction[] newArray(int size) {
            return new VimeoInteraction[size];
        }
    };
}
