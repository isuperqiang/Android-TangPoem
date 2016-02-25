package com.silence.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silence on 2016/2/13 0013.
 */
public class Poem implements Parcelable {
    private int mId;
    private String mTitle;
    private String mAuthor;
    private String mType;
    private String mContent;
    private String mDesc;

    public Poem() {
    }

    public Poem(String title, String author, String type, String content, String desc) {
        mTitle = title;
        mAuthor = author;
        mType = type;
        mContent = content;
        mDesc = desc;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mAuthor);
        dest.writeString(this.mType);
        dest.writeString(this.mContent);
        dest.writeString(this.mDesc);
    }

    protected Poem(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mAuthor = in.readString();
        this.mType = in.readString();
        this.mContent = in.readString();
        this.mDesc = in.readString();
    }

    public static final Parcelable.Creator<Poem> CREATOR = new Parcelable.Creator<Poem>() {
        public Poem createFromParcel(Parcel source) {
            return new Poem(source);
        }

        public Poem[] newArray(int size) {
            return new Poem[size];
        }
    };
}
