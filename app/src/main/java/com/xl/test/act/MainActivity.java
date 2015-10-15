package com.xl.test.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xl.test.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private  BottomFloatListView mBottomFloatListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomFloatListView = (BottomFloatListView)findViewById(R.id.listView)  ;
        mBottomFloatListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        ViewGroup bottomView = (ViewGroup)findViewById(R.id.bottombar) ;
        mBottomFloatListView.setBottomBar(bottomView);
        mBottomFloatListView.setTitleBar(findViewById(R.id.title_bar));
        mBottomFloatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,ValueAnimatorAct.class));
            }
        });
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();

        for(int i = 0; i <100; i++)      {
            data.add("测试数据" + i);
        }

        return data;

    }

  /*  void initBaseTitleBar() {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        if (mTitleBar != null) {
            mTitleBar.setFull(true);
            setTranslucentStatus();
            mTitleBar.setIconOnClickListener(this);
        } else {
            QLog.d(this,"has no title bar.notice.............");
        }
    }*/
}
