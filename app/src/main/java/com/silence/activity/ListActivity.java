package com.silence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.silence.fragment.ListFgt;
import com.silence.poem.R;
import com.silence.pojo.Poem;
import com.silence.utils.Const;

public class ListActivity extends AppCompatActivity implements ListFgt.onPoemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView tvTitle = (TextView) findViewById(R.id.tv_list_title);
        findViewById(R.id.iv_list_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListFgt listFgt = null;
        Intent intent = getIntent();
        int result = intent.getIntExtra(Const.SEARCH_RESULT, 0);
        if (result == Const.TYPE_TYPE) {
            String type = intent.getStringExtra(Const.KEY_TYPE);
            listFgt = ListFgt.newInstance(result, type);
            tvTitle.setText(type);
        } else if (result == Const.TYPE_AUTHOR) {
            String author = intent.getStringExtra(Const.KEY_AUTHOR);
            listFgt = ListFgt.newInstance(result, author);
            tvTitle.setText(author);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.list_main_content, listFgt).commit();
    }

    @Override
    public void getPoem(Poem poem) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.KEY_POEM, poem);
        startActivity(intent);
    }
}
