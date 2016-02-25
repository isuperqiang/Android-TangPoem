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
 * Created by Silence on 2016/2/13 0013.
 */
public class TypeFgt extends ListFragment {

    private onTypeClickListener mOnTypeClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onTypeClickListener) {
            mOnTypeClickListener = (onTypeClickListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemDao poemDao = new PoemDao(getActivity());
        List<String> types = poemDao.getTypes();
        CommonAdapter commonAdapter = new CommonAdapter<String>(types, R.layout.item_auther) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.tv_item_author, obj);
            }
        };
        setListAdapter(commonAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnTypeClickListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mOnTypeClickListener != null) {
            mOnTypeClickListener.getType((String) l.getItemAtPosition(position));
        }
    }

    public interface onTypeClickListener {
        void getType(String type);
    }
}
