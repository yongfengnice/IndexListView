package com.indexlistview.bean;

import android.text.TextUtils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.indexlistview.view.IndexListView;

/**
 * Creator: syf(2499522170@qq.com)
 * Date   : on 2016/11/14 0014
 * Desc   : 实体类
 */
public class TestBean implements Comparable<TestBean> {

    private String mName;
    private String mPinyin;

    public String getPinyin() {
        return mPinyin;
    }

    public void setPinyin(String pinyin) {
        this.mPinyin = pinyin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public TestBean(String name) {
        this.mName = name;
        if (!TextUtils.isEmpty(name)) {
            try {
                this.mPinyin = PinyinHelper.convertToPinyinString(name.trim(), "", PinyinFormat.WITHOUT_TONE);
            } catch (PinyinException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(mPinyin)) {
            this.mPinyin = this.mPinyin.toUpperCase();
            char firstChar = mPinyin.charAt(0);
            if (firstChar < 'A' || firstChar > 'Z') {
                this.mPinyin = TextUtils.concat(IndexListView.sChar_z, this.mPinyin).toString();
            }
        } else {
            this.mPinyin = IndexListView.sChar_z;
        }
    }

    @Override
    public int compareTo(TestBean o) {
        return this.mPinyin.compareTo(o.getPinyin());
    }
}
