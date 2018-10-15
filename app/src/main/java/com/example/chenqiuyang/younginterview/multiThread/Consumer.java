package com.example.chenqiuyang.younginterview.multiThread;

/**
 * TODO:功能说明 消费者
 *
 * @author: chenqiuyang
 * @date: 2018-07-22 15:06
 */
public class Consumer extends Thread{
    private int numConsume;//每次需要生产的商品的数量
    private Storage mStorage ;//仓库

    public Consumer(Storage storage,int numNeed) {
        this.numConsume = numNeed;
        mStorage = storage;
    }


    //调用仓库的生产方法
    private void consume(int num){
        mStorage.consumeGoods(num);
    }


    @Override
    public void run() {
        super.run();
        consume(numConsume);
    }
}
