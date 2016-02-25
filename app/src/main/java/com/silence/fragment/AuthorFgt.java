package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.dao.PoemDao;
import com.silence.poem.R;

import java.util.List;

/**
 * Created by Silence on 2016/2/14 0014.
 */
public class AuthorFgt extends ListFragment {
    private onAuthorClickListener mOnAuthorClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onAuthorClickListener) {
            mOnAuthorClickListener = (onAuthorClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemDao poemDao = new PoemDao(getActivity());
        List<String> authors = poemDao.getAuthors();
        CommonAdapter commonAdapter = new CommonAdapter<String>(authors, R.layout.item_auther) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.tv_item_author, obj);
            }
        };
        setListAdapter(commonAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mOnAuthorClickListener != null) {
            mOnAuthorClickListener.getAuthor((String) l.getItemAtPosition(position));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnAuthorClickListener = null;
    }

    public interface onAuthorClickListener {
        void getAuthor(String auth);
    }
}
