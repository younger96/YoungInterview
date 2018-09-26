package com.example.chenqiuyang.younginterview.design_pettern.decor;

public class RedShapeDecorator extends ShapeDecorator {
 
   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);     
   }
 
   @Override
   public void draw() {
      decoratedShape.draw();         
      setRedBorder(decoratedShape);
   }
 
   private void setRedBorder(Shape decoratedShape){
      System.out.println(decoratedShape.getClass().getSimpleName()+"穿上红色鞋套");
   }
}