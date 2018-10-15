package com.example.chenqiuyang.younginterview.butterkinfe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chenqiuyang.younginterview.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ButterKnifeFragment extends Fragment {
    @BindView(R.id.button2)
    Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_butter_knife, container, false);
        ButterKnife.bind(this, rootView);//这里有些不同
        return rootView;

      }
}