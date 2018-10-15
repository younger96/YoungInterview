package com.example.chenqiuyang.younginterview.multiThread;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chenqiuyang.younginterview.R;

/**
 * TODO:功能说明 生产者消费者demo
 *
 * @author: chenqiuyang
 * @date: 2018-07-22 15:12
 */
public class PCActivity extends Activity {
    EditText etNum ;
    TextView tvPro,tvCon;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc);
        etNum = findViewById(R.id.et_num);
        tvCon = findViewById(R.id.tv_consume);
        tvPro = findViewById(R.id.tv_produce);



//        final Storage storage = new Storage();
//
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Consumer consumer
//                        = new Consumer(storage,Integer.getInteger(etNum.getText().toString()));
//
//
//
//            }
//        });

    }
}
