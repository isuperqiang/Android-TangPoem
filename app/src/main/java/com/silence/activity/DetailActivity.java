package com.silence.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.silence.poem.R;
import com.silence.pojo.Poem;
import com.silence.utils.Const;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        TextView tvAuth = (TextView) findViewById(R.id.tv_detail_author);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        TextView tvContent = (TextView) findViewById(R.id.tv_detail_content);
        findViewById(R.id.iv_detail_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Poem poem = getIntent().getParcelableExtra(Const.KEY_POEM);
        if (poem != null) {
            tvTitle.setText(poem.getTitle());
            tvAuth.setText(poem.getAuthor());
            String[] split = poem.getContent().split("，");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                builder.append(split[i]);
                builder.append(",<br/>");
            }
            builder.append(split[split.length - 1]);
            tvContent.setText(Html.fromHtml(builder.toString()));
            tvDesc.setText(Html.fromHtml(poem.getDesc()));
        }
    }
}
