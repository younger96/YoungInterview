package com.example.chenqiuyang.younginterview.design_pettern.builder.computer;

/**
 * Created by young on 18-3-15.
 */

public class BuilderPattern {
    public static void main(String[] args){

//逛了很久终于发现一家合适的电脑店
//找到该店的老板和装机人员
        Director director = new Director();
        Builder builder = new ConcreteBuilder();

//沟通需求后，老板叫装机人员去装电脑
        director.Construct(builder);

//装完后，组装人员搬来组装好的电脑
        ComputerProduct computer = builder.GetComputer();
//组装人员展示电脑给小成看
        computer.Show();

    }
}
