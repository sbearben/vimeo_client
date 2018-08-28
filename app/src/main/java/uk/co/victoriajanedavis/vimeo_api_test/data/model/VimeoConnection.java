package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VimeoConnection implements Parcelable {

    public static final String OPTION_GET = "GET";
    public static final String OPTION_PUT = "PUT";
    public static final String OPTION_POST = "POST";
    public static final String OPTION_DELETE = "DELETE";

    @SerializedName("uri") @Expose private String uri;
    @SerializedName("options") @Expose private List<String> optionsList;
    @SerializedName("total") @Expose private int total;


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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeList(this.optionsList);
        dest.writeInt(this.total);

    }

    protected VimeoConnection(Parcel in) {
        this.uri = in.readString();
        this.optionsList = new ArrayList<>();
        in.readList(this.optionsList, String.class.getClassLoader());
        //this.optionsList = in.readArrayList(String.class.getClassLoader());
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<VimeoConnection> CREATOR = new Parcelable.Creator<VimeoConnection>() {
        @Override
        public VimeoConnection createFromParcel(Parcel source) {
            return new VimeoConnection(source);
        }

        @Override
        public VimeoConnection[] newArray(int size) {
            return new VimeoConnection[size];
        }
    };
}
