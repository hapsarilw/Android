package com.android.shopmanga;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Entity
public class Manga implements Serializable, Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo
    private String address;
    @ColumnInfo()
    @Nullable
    private double lat;
    @ColumnInfo
    @Nullable
    private double lng;
    @ColumnInfo
    private int price;
    @ColumnInfo
    private String sellerName;
    @ColumnInfo
    private String telephone;
    @ColumnInfo
    private String mangaName;
    @ColumnInfo
    private String imageUrl;
    @ColumnInfo
    private Double volume;


    public Manga(String id,String address, double lat, double lng, int price, String sellerName, String telephone, String mangaName, String imageUrl,Double volume) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.price = price;
        this.sellerName = sellerName;
        this.telephone = telephone;
        this.mangaName = mangaName;
        this.imageUrl = imageUrl;
        this.volume = volume;
    }


    @Ignore
    public Manga(JSONObject json) throws JSONException {
            this.address = json.getJSONObject("map").getJSONObject("address").getJSONObject("map").getString("address");
            this.lat = json.getJSONObject("map").getJSONObject("address").getJSONObject("map").getDouble("lat");
            this.lng = json.getJSONObject("map").getJSONObject("address").getJSONObject("map").getDouble("lng");
            this.price = json.getJSONObject("map").getInt("price");
            this.sellerName = json.getJSONObject("map").getString("sellerName");
            this.telephone = json.getJSONObject("map").getString("telephone");
            this.mangaName= json.getJSONObject("map").getString("mangaName");
            this.imageUrl = json.getJSONObject("map").has("imageUrl") ? json.getJSONObject("map").getString("imageUrl") : null;
            this.volume = json.getJSONObject("map").getDouble("volume");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(price);
        dest.writeString(sellerName);
        dest.writeString(telephone);
        dest.writeString(mangaName);
        dest.writeString(imageUrl);
        dest.writeDouble(volume);
    }
    public static final Parcelable.Creator<Manga> CREATOR
            = new Parcelable.Creator<Manga>() {
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    public Manga(){}
    public Manga(Parcel in){
        address = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        price = in.readInt();
        sellerName = in.readString();
        telephone = in.readString();
        mangaName = in.readString();
        imageUrl = in.readString();
        volume = in.readDouble();
    }
    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE manga "
                    +"ADD COLUMN volume DOUBLE");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
}
