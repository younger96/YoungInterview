package com.example.chenqiuyang.younginterview.customView.util.tasks;

/**
 * Created by walljiang on 2018/5/8.
 */

public interface IVResultCallback {
    //result >= 0 时为ok状态
    void onResult(VBaseOperator operator, int result);
}
