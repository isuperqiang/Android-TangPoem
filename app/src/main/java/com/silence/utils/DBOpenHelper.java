package com.silence.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silence.poem.R;
import com.silence.pojo.Poem;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Silence on 2016/1/30 0030.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper sDBOpenHelper;
    private Context mContext;

    public DBOpenHelper(Context context) {
        super(context, Const.DB_NAME, null, Const.DB_VERSION);
        mContext = context;
    }

    public static DBOpenHelper getInstance(Context context) {
        if (sDBOpenHelper == null) {
            sDBOpenHelper = new DBOpenHelper(context);
        }
        return sDBOpenHelper;
    }

    private ArrayList<Poem> readPoems(Context context) {
        SaxHelper poemSaxHelper = new SaxHelper();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.poems);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputStream, poemSaxHelper);
            inputStream.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return poemSaxHelper.getPoems();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists poem(" +
                "_id integer primary key autoincrement," +
                "title text not null," +
                "author text not null," +
                "type text not null," +
                "content text not null," +
                "description text not null" +
                ");");
        ArrayList<Poem> poems = readPoems(mContext);
        for (int i = 0, j = poems.size(); i < j; i++) {
            Poem poem = poems.get(i);
            db.execSQL("insert into poem (title, author, type, content, description) values (?,?,?,?,?);",
                    new Object[]{poem.getTitle(), poem.getAuthor(), poem.getType(), poem.getContent(),
                            poem.getDesc()});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}