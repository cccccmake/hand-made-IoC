package com.lagou.edu.proxyDemo.jdk;

public class JdkProxy {
    public static void main(String[] args) {
        // instantiate the client
        IRentingHouse rentingHouse = new RentingHouseImpl();
        // get the proxy object DYNAMICALLY
        IRentingHouse proxy =
                (IRentingHouse) ProxyFactory.getInstance().getJdkProxy(rentingHouse);
        proxy.rentHouse();
    }
}
