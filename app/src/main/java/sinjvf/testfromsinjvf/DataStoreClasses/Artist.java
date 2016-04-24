package sinjvf.testfromsinjvf.DataStoreClasses;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import sinjvf.testfromsinjvf.AuxiliaryClasses.ConcatStrings;

/**
 * Класс для хранения данных и передачи их Activity через intent
 */
public class Artist implements Parcelable {
    private int id;
    private String name;
    private String imgUrlBig;
    private String imgUrlSmall;
    private String styles; //all genres  concat to one string
    private int albums;
    private int tracks;
    private String about;
    private String description;
    private String link;


    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrlBig() {
        return imgUrlBig;
    }

    public void setImgUrlBig(String imgURL) {
        this.imgUrlBig = imgURL;
    }

    public String getImgUrlSmall() {
        return imgUrlSmall;
    }

    public void setImgUrlSmall(String imgURL) {
        this.imgUrlSmall = imgURL;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String[] styles) {
        this.styles = ConcatStrings.getStyles(styles);;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //get string with number of albums and tracks
    public String getProgress(String separator, Context context){
        return ConcatStrings.getProgress(albums, tracks, separator, context);
    }
    
    public Artist(int id, String name, String imgUrlBig ,String imgUrlSmall, String[] styles, int albums, int tracks, String about, String description, String link){
        this.id = id;
        this.name =name;
        this.imgUrlBig = imgUrlBig;
        this.imgUrlSmall = imgUrlSmall;
        //all genres concat  to one string
        this.styles = ConcatStrings.getStyles(styles);
        this.albums = albums;
        this.tracks = tracks;
        this.about = about;
        this.description = description;
        this.link = link;
    }

    public Artist(){
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(imgUrlBig);
        parcel.writeString(imgUrlSmall);
        parcel.writeString(styles);
        parcel.writeInt(albums);
        parcel.writeInt(tracks);
        parcel.writeString(about);
        parcel.writeString(description);
        parcel.writeString(link);
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
    // конструктор, считывающий данные из Parcel
    private Artist(Parcel parcel) {
        name = parcel.readString();
        imgUrlBig = parcel.readString();
        imgUrlSmall = parcel.readString();
        styles = parcel.readString();
        albums = parcel.readInt();
        tracks = parcel.readInt();
        about = parcel.readString();
        description =parcel.readString();
        link = parcel.readString();
    }
}
