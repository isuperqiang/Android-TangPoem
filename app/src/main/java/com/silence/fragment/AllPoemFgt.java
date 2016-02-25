package com.silence.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.silence.adapter.CommonAdapter;
import com.silence.dao.PoemDao;
import com.silence.poem.R;
import com.silence.pojo.Poem;

import java.util.List;

/**
 * Created by Silence on 2016/2/13 0013.
 */
public class AllPoemFgt extends ListFragment {

    private onPoemClickListener mOnPoemClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onPoemClickListener) {
            mOnPoemClickListener = (onPoemClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemDao poemDao = new PoemDao(getActivity());
        List<Poem> allPoems = poemDao.getAllPoems();
        CommonAdapter commonAdapter = new CommonAdapter<Poem>(allPoems, R.layout.item_poem) {
            @Override
            public void bindView(ViewHolder holder, Poem obj) {
                holder.setText(R.id.tv_list_title, obj.getTitle());
                holder.setText(R.id.tv_list_author, " - " + obj.getAuthor());
                holder.setText(R.id.tv_list_type, obj.getType());
            }
        };
        setListAdapter(commonAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mOnPoemClickListener != null) {
            mOnPoemClickListener.getPoem((Poem) l.getItemAtPosition(position));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnPoemClickListener = null;
    }

    public interface onPoemClickListener {
        void getPoem(Poem poem);
    }
}