package com.evoting;

import com.evoting.model.States;
import com.evoting.service.StatesService;

public class Application {

  public static void main(String[] args) {
    States states1 = new StatesService().registerStates("Selangor");
    States states2 =new StatesService().registerStates("Penang");

    System.out.println(states1.toString());
    System.out.println(states2.toString());
  }
}
