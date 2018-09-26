package com.example.chenqiuyang.younginterview.design_pettern.decor;

public class DecoratorPatternDemo {
   public static void main(String[] args) {
 
      Shape hero = new Hero();

      Shape redShoot = new RedShapeDecorator(hero);
      hero.draw();
      redShoot.draw();
   }
}