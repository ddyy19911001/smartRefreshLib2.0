package com.dy.refreshlibup;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.help.MyQuckAdapter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.viewimpl.MyFooterView;
import com.scwang.smartrefresh.layout.viewimpl.MyHeaderView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnLoadMoreListener, OnRefreshListener {
    private SmartRefreshLayout smRf;
    private RecyclerView rcView;
    private MyQuckAdapter<String> adapter;
    private List<String> datas=new ArrayList<>();


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.normal_bg, R.color.normal_6a);//全局设置主题颜色
                MyHeaderView head = new MyHeaderView(context);
                return head;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setPrimaryColorsId(R.color.normal_bg,R.color.normal_6a);//全局设置主题颜色
                MyFooterView footer = new MyFooterView(context);
                return footer;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<20;i++) {
            datas.add("数据1");
            datas.add("数据2");
        }
        smRf = (SmartRefreshLayout) findViewById(R.id.smRf);
        rcView = (RecyclerView) findViewById(R.id.rcView);
        smRf.setOnLoadMoreListener(this);
        smRf.setOnRefreshListener(this);
        setAdapter();
    }

    private void setAdapter() {
        if(adapter==null){
            adapter=new MyQuckAdapter<String>(R.layout.item_text,datas,this) {
                @Override
                protected void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_test,item);
                }
            };
            LinearLayoutManager manager=new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rcView.setLayoutManager(manager);
            rcView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }


    public void getData(boolean isRefresh){
        List<String> getDatas=new ArrayList<>();
        if(isRefresh){
            datas.clear();
            for(int i=0;i<20;i++) {
                getDatas.add("数据1");
                getDatas.add("数据2");
            }
            smRf.finishRefresh();
        }else {
            if (datas.size() <= 40) {
                getDatas.add("加载更多");
                getDatas.add("加载更多");
                getDatas.add("加载更多");
                getDatas.add("加载更多");
                getDatas.add("加载更多");
                getDatas.add("加载更多");
                smRf.finishLoadMore();
            }else{
                smRf.finishLoadMoreWithNoMoreData();
            }
        }
        datas.addAll(getDatas);
        setAdapter();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData(true);
    }
}
