package com.lagou.edu.factory;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
        /*private ProxyFactory() {
    }

    private static ProxyFactory proxyFactory = new ProxyFactory();

    public static ProxyFactory getInstance() {
        return proxyFactory;
    }*/

    /**
     * @param client the client
     * @return returns the proxy
     */
    public Object getJdkProxy(Object client) {
        // get the proxy object DYNAMICALLY
        Object proxy =
                Proxy.newProxyInstance(client.getClass().getClassLoader(),
                        client.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                Object res = null;
                                try {
                                    // begin of the trx <=> close the autocommit
                                    transactionManager.beginTransaction();
                                    res = method.invoke(client, args);
                                    // commit trx
                                    transactionManager.commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // trx rollback
                                    transactionManager.rollback();
                                    throw e;
                                }
                                return res;
                            }
                        });
        return proxy;
    }
}
