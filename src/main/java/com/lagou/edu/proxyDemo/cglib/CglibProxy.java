package com.lagou.edu.proxyDemo.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy {
    public static void main(String[] args) {
        RentingHouseImpl rentingHouse = new RentingHouseImpl();
        RentingHouseImpl rentingHouseProxy =
                (RentingHouseImpl) Enhancer.create(rentingHouse.getClass(), new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        Object res = null;
                        System.out.println("===>");
                        res = method.invoke(rentingHouse, objects);
                        System.out.println("<===");
                        return res;
                    }
                });
        rentingHouseProxy.rentHouse();
    }
}
