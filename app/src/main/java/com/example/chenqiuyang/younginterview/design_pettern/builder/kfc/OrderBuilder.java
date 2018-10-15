package com.example.chenqiuyang.younginterview.design_pettern.builder.kfc;

public class OrderBuilder {
    private Burger mBurger;
    private Suit mSuit;

    //单点汉堡,num为数量
    public OrderBuilder burger(Burger burger, int num) {
        mBurger = burger;
        return this;
    }

    //点套餐，实际中套餐也可以点多份
    public OrderBuilder suit(Suit suit, int num) {
        mSuit = suit;
        return this;
    }

    //完成订单
    public Order build() {
        Order order = new Order();
        if (mSuit!=null){
            order.setSuit(mSuit);
        }

        if (mBurger!=null){
            order.setBurger(mBurger);
        }
        return order;
    }
}