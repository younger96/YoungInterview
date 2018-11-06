
package com.example.chenqiuyang.younginterview.customView.util.tasks;

/**
 * Created by walljiang on 2018/5/8.
 */

public interface IVOperator {

    void start(IVResultCallback callback);

    void removeCallback();

    boolean isProccessing();
}
