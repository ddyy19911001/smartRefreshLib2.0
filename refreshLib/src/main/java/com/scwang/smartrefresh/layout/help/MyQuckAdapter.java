package com.scwang.smartrefresh.layout.help;

import android.content.Context;

import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public abstract class MyQuckAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public Context context;
    public MyQuckAdapter(int layoutResId, @Nullable List<T> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }


    @Override
    protected abstract void convert(BaseViewHolder helper, T item);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
