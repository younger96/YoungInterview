package com.example.chenqiuyang.younginterview.multiThread;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO:功能说明 仓库类，实现缓冲存储货物
 *
 * @author: chenqiuyang
 * @date: 2018-07-22 11:28
 */
public class Storage {
    private static int MAX_SIZE = 200;//仓库的最大存储量
    private static int MIN_SIZE = 5;//仓库的最小备货量

//    private int mGoodsNum; //目前货物数量
//    public int getGoodsNum() {
//        return mGoodsNum;
//    }

    public static LinkedList getmList() {
        return mList;
    }

    private static LinkedList mList = new LinkedList();//存储货物的集合


    public Storage() {
    }

    public Storage(int maxSize, int minSize) {
        MAX_SIZE = maxSize;
        MIN_SIZE = minSize;
    }

    /**
     * 生产n个商品
     * @param num 需要生产的商品数量
     */
    public void pruduceGoods(int num){
        //线程同步mList
        synchronized (mList){
            System.out.println("【要生产的产品数量】:" + num + "/t【库存量】:"
                    + mList.size() + "/t暂时不能执行生产任务!");

            //生产不满足条件，线程等待
            while (mList.size()+num>=MAX_SIZE){
               try {
                   //停止生产
                   mList.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }

            //满足条件，生产
            for (int i = 0; i < num; i++) {
                mList.add(new Object());
            }
        }


        System.out.println("【已经生产产品数】:" + num + "/t【现仓储量为】:" + mList.size());
        mList.notifyAll();
    }


    /**
     * 消费n个商品
     * @param num 消费的商品数量
     */
    public  void consumeGoods(int num){
        //线程同步mList
        synchronized (mList){

            // 如果仓库存储量不足
            while (mList.size() < num)
            {
                System.out.println("【要消费的产品数量】:" + num + "/t【库存量】:"
                        + mList.size() + "/t暂时不能执行生产任务!");
                try
                {
                    // 由于条件不满足，消费阻塞
                    mList.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i)
            {
                mList.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "/t【现仓储量为】:" + mList.size());

            mList.notifyAll();
        }
    }




}
