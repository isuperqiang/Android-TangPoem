package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.poem.R;
import com.silence.pojo.Poem;
import com.silence.utils.Const;

import java.util.List;

/**
 * Created by Silence on 2016/2/14 0014.
 */
public class SearchFgt extends ListFragment {

    private onAuthClickListener mOnAuthClickListener;
    private onPoemClickListener mOnPoemClickListener;
    private int mCurIndex;
    private CommonAdapter mCommonAdapter;

    public static SearchFgt newInstance(int curIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(Const.KEY_INDEX, curIndex);
        SearchFgt searchFgt = new SearchFgt();
        searchFgt.setArguments(bundle);
        return searchFgt;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCurIndex = getArguments().getInt(Const.KEY_INDEX, 0);
        if (mCurIndex == 1) {
            if (context instanceof onAuthClickListener) {
                mOnAuthClickListener = (onAuthClickListener) context;
            }
        } else {
            if (context instanceof onPoemClickListener) {
                mOnPoemClickListener = (onPoemClickListener) context;
            }
        }
    }

    public void refreshAuth(List<String> authors) {
        mCommonAdapter.setData(authors);
    }

    public void refreshPoem(List<Poem> poems) {
        mCommonAdapter.setData(poems);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCurIndex == 1) {
            mCommonAdapter = new CommonAdapter<String>(null, R.layout.item_auther) {
                @Override
                public void bindView(ViewHolder holder, String obj) {
                    holder.setText(R.id.tv_item_author, obj);
                }
            };
        } else {
            mCommonAdapter = new CommonAdapter<Poem>(null, R.layout.item_poem) {
                @Override
                public void bindView(ViewHolder holder, Poem obj) {
                    holder.setText(R.id.tv_list_title, obj.getTitle());
                    holder.setText(R.id.tv_list_author, " - " + obj.getAuthor());
                    holder.setText(R.id.tv_list_type, obj.getType());
                }
            };
        }
        setListAdapter(mCommonAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mCurIndex == 1) {
            if (mOnAuthClickListener != null) {
                mOnAuthClickListener.getAuthor((String) l.getItemAtPosition(position));
            }
        } else {
            if (mOnPoemClickListener != null) {
                mOnPoemClickListener.getPoem((Poem) l.getItemAtPosition(position));
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnAuthClickListener = null;
        mOnPoemClickListener = null;
    }

    public interface onAuthClickListener {
        void getAuthor(String author);
    }

    public interface onPoemClickListener {
        void getPoem(Poem poem);
    }

}
