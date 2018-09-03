package com.example.chenqiuyang.younginterview.bitmaploader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenqiuyang.younginterview.R;

public class BitmapActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        Glide.with(this)
                .load("")
                .into(imageView);
    }
}
