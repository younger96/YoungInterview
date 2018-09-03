package com.example.chenqiuyang.younginterview.design_pettern.observe;

public class ObserverPatternDemo {
   public static void main(String[] args) {
      Subject subject = new Subject();

      HexaObserver hex = new HexaObserver();
      hex.subscribe(subject);


      new OctalObserver(subject);
      new BinaryObserver(subject);

      System.out.println("First state change: 15");    
      subject.setState(15);
      System.out.println("Second state change: 10");    
      subject.setState(10);
   }
}