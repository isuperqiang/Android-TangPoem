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
import com.silence.utils.Const;

import java.util.List;

/**
 * Created by Silence on 2016/2/14 0014.
 */
public class ListFgt extends ListFragment {
    private onPoemClickListener mOnPoemClickListener;

    public static ListFgt newInstance(int type, String key) {
        Bundle bundle = new Bundle();
        bundle.putInt(Const.SEARCH_RESULT, type);
        bundle.putString(Const.KEY_TYPE, key);
        ListFgt listFgt = new ListFgt();
        listFgt.setArguments(bundle);
        return listFgt;
    }

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
        List<Poem> poems = null;
        int from = getArguments().getInt(Const.SEARCH_RESULT);
        String key = getArguments().getString(Const.KEY_TYPE);
        if (from == Const.TYPE_AUTHOR) {
            poems = poemDao.queryPoemsByAuthor(key);
        } else if (from == Const.TYPE_TYPE) {
            poems = poemDao.queryPoemsByType(key);
        }
        CommonAdapter commonAdapter = new CommonAdapter<Poem>(poems, R.layout.item_poem) {
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
    public void onDetach() {
        super.onDetach();
        mOnPoemClickListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mOnPoemClickListener != null) {
            mOnPoemClickListener.getPoem((Poem) l.getItemAtPosition(position));
        }
    }

    public interface onPoemClickListener {
        void getPoem(Poem poem);
    }
}
