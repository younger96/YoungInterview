package com.example.chenqiuyang.younginterview.design_pettern.observe;

public class HexaObserver extends Observer{
   public HexaObserver(){

   }

   public void subscribe(Subject subject){
       subject.attach(this);
   }


   @Override
   public void update() {
      System.out.println( "Hex String: " 
      + Integer.toHexString( subject.getState() ).toUpperCase() ); 
   }

}