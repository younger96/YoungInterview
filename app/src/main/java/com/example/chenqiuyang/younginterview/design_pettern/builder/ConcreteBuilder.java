package com.example.chenqiuyang.younginterview.design_pettern.builder;

//装机人员1
  public class ConcreteBuilder extends Builder{
    //创建产品实例
    private ComputerProduct computer = new ComputerProduct();

    //组装产品
    @Override
    public void  BuildCPU(){  
       computer.Add("组装CPU");
    }  

    @Override
    public void  BuildMainboard(){  
       computer.Add("组装主板");
    }  

    @Override
    public void  BuildHD(){  
       computer.Add("组装主板");
    }  

    //返回组装成功的电脑
     @Override
      public  ComputerProduct GetComputer(){
      return computer;
    }  
}