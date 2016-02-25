package com.silence.utils;

import com.silence.pojo.Poem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Silence on 2016/2/13 0013.
 */
public class SaxHelper extends DefaultHandler {
    private Poem mPoem;
    private ArrayList<Poem> mPoems;
    private String mTagName;
    private StringBuilder mDesc;

    @Override
    public void startDocument() throws SAXException {
        mPoems = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("node")) {
            mPoem = new Poem();
        }
        mTagName = localName;
        mDesc = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        if ("title".equals(mTagName)) {
            mPoem.setTitle(data);
        } else if ("auth".equals(mTagName)) {
            mPoem.setAuthor(data);
        } else if ("type".equals(mTagName)) {
            mPoem.setType(data);
        } else if ("content".equals(mTagName)) {
            mPoem.setContent(data);
        } else if ("desc".equals(mTagName)) {
            mDesc.append(data);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("node")) {
            mPoem.setDesc(mDesc.toString());
            mPoems.add(mPoem);
            mDesc = null;
            mPoem = null;
        }
        mTagName = null;
    }

    public ArrayList<Poem> getPoems() {
        return mPoems;
    }
}
