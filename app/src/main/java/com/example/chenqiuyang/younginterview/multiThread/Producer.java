package com.example.chenqiuyang.younginterview.multiThread;

/**
 * TODO:功能说明 生产者类
 *
 * @author: chenqiuyang
 * @date: 2018-07-22 14:56
 */
public class Producer extends Thread{

    private int numNeedProduce;//每次需要生产的商品的数量
    private Storage mStorage ;//仓库

    public Producer(Storage storage,int numNeed) {
        this.numNeedProduce = numNeed;
        mStorage = storage;
    }


    //调用仓库的生产方法
    private void produce(int num){
        mStorage.pruduceGoods(num);
    }


    @Override
    public void run() {
        super.run();
        produce(numNeedProduce);
    }
}
