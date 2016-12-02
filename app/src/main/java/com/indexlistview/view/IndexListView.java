package com.indexlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Creator: syf(205205)
 * Date   : on 2016/12/2 0002
 * Desc   :
 */
public class IndexListView extends ListView {
    public static final String sChar_z = "z";
    public static final String sFirstChar = "↑";
    public static final String sLastChar = "#";

    private int mCellHeight;
    private ListAdapter mListAdapter;

    public IndexListView(Context context) {
        this(context, null);
    }

    public IndexListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mPreIndex;
    private int mCurrentIndex;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mListAdapter == null) {
            mListAdapter = getAdapter();
            mCellHeight = Math.round(getHeight() * 1.0f / mListAdapter.getCount());
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mCurrentIndex = (int) (ev.getY() / mCellHeight);
                if (mCurrentIndex < 0 || mCurrentIndex >= mListAdapter.getCount()) {
                    actionUp();
                    break;
                }

                if (mCurrentIndex != mPreIndex && mListener != null) {
                    mListener.onLetterUpdate(mCurrentIndex);
                    mPreIndex = mCurrentIndex;
                }
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void actionUp() {
        mPreIndex = -1;
        mCurrentIndex = -1;
        if (mListener != null) {
            mListener.onLetterNone();
        }
    }

    public interface OnLetterUpdateListener {
        void onLetterUpdate(int letterIndex);

        void onLetterNone();
    }

    private OnLetterUpdateListener mListener;

    public void setOnLetterUpdateListener(OnLetterUpdateListener listener) {
        mListener = listener;
    }
}
