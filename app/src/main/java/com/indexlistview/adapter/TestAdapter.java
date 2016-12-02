package com.indexlistview.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indexlistview.R;
import com.indexlistview.bean.TestBean;
import com.indexlistview.view.IndexListView;

import java.util.ArrayList;

/**
 * Creator: syf(2499522170@qq.com)
 * Date   : on 2016/11/14 0014
 * Desc   : 列表数据适配器
 */
public class TestAdapter extends BaseAdapter {

    private ArrayList<TestBean> mPersonList;

    public TestAdapter(ArrayList<TestBean> personList) {
        mPersonList = personList;
    }

    @Override
    public int getCount() {
        return mPersonList == null ? 0 : mPersonList.size();
    }

    @Override
    public TestBean getItem(int position) {
        return mPersonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(viewGroup.getContext(), R.layout.item_main, null);
            holder = new ViewHolder(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String str = null;
        String curIndex = String.valueOf(getItem(position).getPinyin().charAt(0));
        if (position == 0) {
            str = curIndex;
        } else {
            String preIndex = String.valueOf(getItem(position - 1).getPinyin().charAt(0));
            if (!TextUtils.equals(curIndex, preIndex)) {
                str = curIndex;
            }
        }
        holder.tvIndex.setVisibility(str == null ? View.GONE : View.VISIBLE);

        holder.tvName.setText(getItem(position).getName());
        holder.tvIndex.setText(IndexListView.sChar_z.equals(curIndex) ? IndexListView.sLastChar : curIndex);
        return convertView;
    }

    final class ViewHolder {
        TextView tvIndex;
        TextView tvName;

        public ViewHolder(View convertView) {
            tvIndex = (TextView) convertView.findViewById(R.id.tv_index);
            tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(this);
        }
    }
}
