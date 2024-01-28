package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;
import com.lagou.edu.factory.BeanFactory;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.TransactionManager;

/**
 * @author 应癫
 */
public class TransferServiceImpl implements TransferService {

    // the keyword "new" has couped the service implementation
    // "TransferServiceImpl" with the Dao's implementation "JdbcAccountDaoImpl"
    //    private AccountDao accountDao = new JdbcAccountDaoImpl();
    // ================= IMPROVEMENT =======================
    //    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");
    // ================= IMPROVEMENT =======================
    // something better!
    // howto init value within the class => set / constructor
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // the consistency in service level here is not guaranteed!
    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        /*try {
            // begin of the trx <=> close the autocommit
        transactionManager.beginTransaction();*/
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney() - money);
            to.setMoney(to.getMoney() + money);

            // - trx happens in DAO! => arrange trx at service level!
            // - 2 connections are used! => use the unique connection!
            accountDao.updateAccountByCardNo(to);
//            int c = 1 / 0;
            accountDao.updateAccountByCardNo(from);
            /*// commit trx
        transactionManager.commit();
        } catch (Exception e){
            e.printStackTrace();
            // trx rollback
            TransactionManager.getInstance().rollback();
            // to allow the servlet to catch the exception
            throw e;
        }*/
    }
}
