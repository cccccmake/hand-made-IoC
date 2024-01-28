package com.lagou.edu.proxyDemo.cglib;

/**
 * The client class
 */
public class RentingHouseImpl implements IRentingHouse {
    @Override
    public void rentHouse() {
        System.out.println("I am the CLIENT :)");
    }
}
