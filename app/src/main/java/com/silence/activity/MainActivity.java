package com.silence.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.silence.dao.PoemDao;
import com.silence.fragment.AllPoemFgt;
import com.silence.fragment.AuthorFgt;
import com.silence.fragment.SearchFgt;
import com.silence.fragment.TypeFgt;
import com.silence.poem.R;
import com.silence.pojo.Poem;
import com.silence.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        AllPoemFgt.onPoemClickListener, TypeFgt.onTypeClickListener, AuthorFgt.onAuthorClickListener,
        SearchView.OnQueryTextListener, SearchFgt.onAuthClickListener, SearchFgt.onPoemClickListener {
    private FragmentManager mFragmentManager;
    private AllPoemFgt mAllPoemFgt;
    private AuthorFgt mAuthFgt;
    private TypeFgt mTypeFgt;
    private SearchFgt mSearchFgt;
    private SearchView mSearchView;
    private List<Fragment> mFragments;
    private TextView mActionBarTitle;
    private RadioGroup mRadioGroup;
    private ImageView mIvBack;
    private int mCurIndex;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initViews();
    }

    private void initViews() {
        mActionBarTitle = (TextView) findViewById(R.id.tv_main_title);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mIvBack = (ImageView) findViewById(R.id.iv_main_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearchFgt();
            }
        });
        mSearchView.setOnQueryTextListener(MainActivity.this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionBarTitle.setVisibility(View.GONE);
                mRadioGroup.setVisibility(View.GONE);
                mIvBack.setVisibility(View.VISIBLE);
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                hideAllFgt(transaction);
                mSearchFgt = SearchFgt.newInstance(mCurIndex);
                transaction.add(R.id.main_content, mSearchFgt);
                transaction.commit();
                switch (mCurIndex) {
                    case 1:
                        mSearchView.setQueryHint(getString(R.string.query_hint_author));
                        break;
                    case 0:
                    case 2:
                        mSearchView.setQueryHint(getString(R.string.query_hint_title));
                        break;
                }
            }
        });
        mFragments = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            mFragments.add(null);
        }
        mRadioGroup = (RadioGroup) findViewById(R.id.main_bottom);
        mRadioGroup.setOnCheckedChangeListener(this);
        RadioButton radioButton = (RadioButton) findViewById(R.id.radio_all);
        radioButton.setChecked(true);
    }

    private void hideSearchFgt() {
        mSearchView.setQuery(null, false);
        mSearchView.setIconified(true);
        mActionBarTitle.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.VISIBLE);
        mIvBack.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(mSearchFgt);
        mSearchFgt = null;
        transaction.show(mFragments.get(mCurIndex));
        transaction.commit();
    }

    private void doSearch(String newText) {
        PoemDao poemDao = new PoemDao(this);
        switch (mCurIndex) {
            case 1:
                List<String> authors = null;
                if (!TextUtils.isEmpty(newText)) {
                    authors = poemDao.queryAuthor(newText);
                }
                mSearchFgt.refreshAuth(authors);
                break;
            case 0:
            case 2:
                List<Poem> poems = null;
                if (!TextUtils.isEmpty(newText)) {
                    poems = poemDao.queryPoem(newText);
                }
                mSearchFgt.refreshPoem(poems);
                break;
        }
    }

    @Override
    public void getPoem(Poem poem) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.KEY_POEM, poem);
        startActivity(intent);
    }

    private void hideAllFgt(FragmentTransaction transaction) {
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFgt(transaction);
        switch (checkedId) {
            case R.id.radio_all:
                mActionBarTitle.setText(R.string.top_all);
                if (mAllPoemFgt == null) {
                    mAllPoemFgt = new AllPoemFgt();
                    mFragments.set(0, mAllPoemFgt);
                    transaction.add(R.id.main_content, mAllPoemFgt);
                } else {
                    transaction.show(mAllPoemFgt);
                }
                mCurIndex = 0;
                break;
            case R.id.radio_auth:
                mActionBarTitle.setText(R.string.top_auth);
                if (mAuthFgt == null) {
                    mAuthFgt = new AuthorFgt();
                    mFragments.set(1, mAuthFgt);
                    transaction.add(R.id.main_content, mAuthFgt);
                } else {
                    transaction.show(mAuthFgt);
                }
                mCurIndex = 1;
                break;
            case R.id.radio_type:
                mActionBarTitle.setText(R.string.top_type);
                if (mTypeFgt == null) {
                    mTypeFgt = new TypeFgt();
                    mFragments.set(2, mTypeFgt);
                    transaction.add(R.id.main_content, mTypeFgt);
                } else {
                    transaction.show(mTypeFgt);
                }
                mCurIndex = 2;
                break;
        }
        transaction.commit();
    }

    @Override
    public void getAuthor(String author) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Const.SEARCH_RESULT, Const.TYPE_AUTHOR);
        intent.putExtra(Const.KEY_AUTHOR, author);
        startActivity(intent);
    }

    @Override
    public void getType(String type) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Const.SEARCH_RESULT, Const.TYPE_TYPE);
        intent.putExtra(Const.KEY_TYPE, type);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        doSearch(newText);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mSearchFgt != null) {
                hideSearchFgt();
            } else if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, R.string.exit_hint, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
