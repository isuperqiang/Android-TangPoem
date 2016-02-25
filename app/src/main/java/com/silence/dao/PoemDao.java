package com.silence.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.silence.pojo.Poem;
import com.silence.utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/13 0013.
 */
public class PoemDao {
    private DBOpenHelper mDBOpenHelper;

    public PoemDao(Context context) {
        mDBOpenHelper = DBOpenHelper.getInstance(context);
    }

    public List<String> queryAuthor(String filter) {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<String> authors = null;
        Cursor cursor = db.rawQuery("select distinct author from poem where author like ?;",
                new String[]{"%" + filter + "%"});
        if (cursor.moveToFirst()) {
            authors = new ArrayList<>(cursor.getCount());
            do {
                String author = cursor.getString(cursor.getColumnIndex("author"));
                authors.add(author);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return authors;
    }

    public List<Poem> queryPoemsByAuthor(String author) {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<Poem> poems = null;
        Cursor cursor = db.rawQuery("select title, type, content, description from poem where author=?;"
                , new String[]{author});
        if (cursor.moveToFirst()) {
            poems = new ArrayList<>(cursor.getCount());
            Poem poem;
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                poem = new Poem(title, author, type, content, description);
                poems.add(poem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return poems;
    }

    public List<Poem> queryPoemsByType(String type) {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<Poem> poems = null;
        Cursor cursor = db.rawQuery("select title, author, content, description from poem where type=?;"
                , new String[]{type});
        if (cursor.moveToFirst()) {
            poems = new ArrayList<>(cursor.getCount());
            Poem poem;
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                poem = new Poem(title, author, type, content, description);
                poems.add(poem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return poems;
    }

    public List<Poem> queryPoem(String filter) {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<Poem> poems = null;
        Cursor cursor = db.rawQuery("select title, author, type, content, description from poem where title like ?;"
                , new String[]{"%" + filter + "%"});
        if (cursor.moveToFirst()) {
            poems = new ArrayList<>(cursor.getCount());
            Poem poem;
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                poem = new Poem(title, author, type, content, description);
                poems.add(poem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return poems;
    }

    public List<Poem> getAllPoems() {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<Poem> poems = null;
        Cursor cursor = db.rawQuery("select title, author, type, content, description from poem;", null);
        if (cursor.moveToFirst()) {
            poems = new ArrayList<>(cursor.getCount());
            Poem poem;
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                poem = new Poem(title, author, type, content, description);
                poems.add(poem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return poems;
    }

    public List<String> getAuthors() {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<String> authors = null;
        Cursor cursor = db.rawQuery("select distinct author from poem order by author desc;", null);
        if (cursor.moveToFirst()) {
            authors = new ArrayList<>(cursor.getCount());
            do {
                String author = cursor.getString(cursor.getColumnIndex("author"));
                authors.add(author);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return authors;
    }

    public List<String> getTypes() {
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        List<String> types = null;
        Cursor cursor = db.rawQuery("select distinct type from poem;", null);
        if (cursor.moveToFirst()) {
            types = new ArrayList<>(cursor.getCount());
            do {
                String author = cursor.getString(cursor.getColumnIndex("type"));
                types.add(author);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return types;
    }
}
