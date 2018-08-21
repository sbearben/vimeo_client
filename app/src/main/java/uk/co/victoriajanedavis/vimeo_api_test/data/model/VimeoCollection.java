package uk.co.victoriajanedavis.vimeo_api_test.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VimeoCollection<T extends Parcelable> implements Parcelable {

    @SerializedName("total") @Expose private int total;
    @SerializedName("page") @Expose private int page;
    @SerializedName("per_page") @Expose private int per_page;
    @SerializedName("paging") @Expose private VimeoPaging paging;
    @SerializedName("data") @Expose private List<T> dataList;

    public VimeoCollection() {
        dataList = new ArrayList<>();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return per_page;
    }

    public void setPerPage(int per_page) {
        this.per_page = per_page;
    }

    public VimeoPaging getPaging() {
        return paging;
    }

    public void setPaging(VimeoPaging paging) {
        this.paging = paging;
    }

    public List<T> getData() {
        return dataList;
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.page);
        dest.writeInt(this.per_page);
        dest.writeParcelable(this.paging, flags);

        if (dataList == null || dataList.size() == 0)
            dest.writeInt(0);

        else {
            dest.writeInt(dataList.size());

            final Class<?> objectsType = dataList.get(0).getClass();
            dest.writeSerializable(objectsType);

            dest.writeList(dataList);
        }
    }

    public VimeoCollection(Parcel in) {
        this.total = in.readInt();
        this.page = in.readInt();
        this.per_page = in.readInt();
        this.paging = in.readParcelable(VimeoPaging.class.getClassLoader());

        int size = in.readInt();
        if (size == 0) {
            this.dataList = null;
        }

        else {
            Class<?> type = (Class<?>) in.readSerializable();

            dataList = new ArrayList<>(size);
            in.readList(dataList, type.getClassLoader());
        }
    }

    public static final Parcelable.Creator<VimeoCollection> CREATOR = new Parcelable.Creator<VimeoCollection>() {
        public VimeoCollection createFromParcel(Parcel in) {
            return new VimeoCollection(in);
        }

        public VimeoCollection[] newArray(int size) {
            return new VimeoCollection[size];
        }
    };
}

