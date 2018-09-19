package com.example.chenqiuyang.younginterview.list.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chenqiuyang.younginterview.R;

public class ListViewActivity extends AppCompatActivity {

    private ListView mListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mListview = (ListView) findViewById(R.id.listview);
        mListview.setAdapter(new MyAdapter(this));
    }


}

