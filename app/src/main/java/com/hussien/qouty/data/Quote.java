package com.hussien.qouty.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotes", indices = {@Index(value = {"lang", "tags", "loved","currently_show"})})
public class Quote implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private final int id;

    private final String text;
    private final String author;
    private String lang;
    private String tags;
    private boolean loved;
    private boolean currently_show;

    public Quote(int id, String text, String author, String lang, String tags, boolean loved) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.lang = lang;
        this.tags = tags;
        this.loved = loved;
    }

    protected Quote(Parcel in) {
        id = in.readInt();
        text = in.readString();
        author = in.readString();
        lang = in.readString();
        tags = in.readString();
        loved = in.readByte() != 0;
        currently_show = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(author);
        dest.writeString(lang);
        dest.writeString(tags);
        dest.writeByte((byte) (loved ? 1 : 0));
        dest.writeByte((byte) (currently_show ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    public void setLoved(boolean loved) {
        this.loved = loved;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTags() {
        return tags;
    }

    public boolean isLoved() {
        return loved;
    }

    public String getLang() {
        return lang;
    }



    public boolean isCurrently_show() {
        return currently_show;
    }

    public void setCurrently_show(boolean currently_show) {
        this.currently_show = currently_show;
    }
}