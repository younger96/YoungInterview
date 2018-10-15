package com.example.chenqiuyang.younginterview.design_pettern.observe;

public abstract class Observer {
   protected Subject subject;
   public abstract void update();
}