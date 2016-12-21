package com.indexlistview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.indexlistview.adapter.TestAdapter;
import com.indexlistview.bean.TestBean;
import com.indexlistview.data.TestData;
import com.indexlistview.view.IndexListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creator: syf(2499522170@qq.com)
 * Date   : on 2016/11/14 0014
 * Desc   : MainActivity
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private IndexListView mIndexLv;
    private TextView mTextView;
    private ArrayList<TestBean> mPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_main);
        mListView.addHeaderView(View.inflate(this, R.layout.view_header, null));
        mTextView = (TextView) findViewById(R.id.tv_center);

        mIndexLv = (IndexListView) findViewById(R.id.index_lv);
    }


    private void initData() {
        initIndexLv();

        for (int i = 0; i < TestData.NAMES.length; i++) {
            mPersonList.add(new TestBean(TestData.NAMES[i]));
        }
        Collections.sort(mPersonList);
        mListView.setAdapter(new TestAdapter(mPersonList));
    }

    private List<String> mLetterList = new ArrayList<>();

    private void initIndexLv() {
        mLetterList.add(IndexListView.sFirstChar);
        for (int i = 0, size = 26; i < size; ++i) {
            mLetterList.add(String.valueOf((char) ('A' + i)));
        }
        mLetterList.add(IndexListView.sLastChar);

        mIndexLv.setAdapter(new ArrayAdapter<String>(this, R.layout.item_bar, R.id.letter, mLetterList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(mLetterList.get(position));
                return textView;
            }
        });

        mIndexLv.setOnLetterUpdateListener(new IndexListView.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(int letterIndex) {
                mIndexLv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.index_bar_press));
                String letter = mLetterList.get(letterIndex);
                showToast(letter);

                if (IndexListView.sFirstChar.equals(letter)) {
                    mListView.setSelection(0);
                    return;
                }
                for (int i = 0; i < mPersonList.size(); i++) {
                    String index = String.valueOf(mPersonList.get(i).getPinyin().charAt(0));
                    if (IndexListView.sLastChar.equals(letter) && TextUtils.equals(IndexListView.sChar_z, index) || TextUtils.equals(letter, index)) {
                        mListView.setSelection(i + mListView.getHeaderViewsCount());
                        break;
                    }
                }
            }

            @Override
            public void onLetterNone() {
                mIndexLv.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.index_bar_normal));
                mTextView.setVisibility(View.GONE);
            }
        });
    }

    private void showToast(String letter) {
        mTextView.setText(letter);
        mTextView.setVisibility(View.VISIBLE);
    }
}
