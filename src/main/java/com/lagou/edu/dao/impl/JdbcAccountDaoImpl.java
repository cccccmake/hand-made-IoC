package com.lagou.edu.dao.impl;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 应癫
 */
public class JdbcAccountDaoImpl implements AccountDao {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        // 从连接池获取连接 => query the bound connection from the current thread
        Connection con = connectionUtils.getCurrentThreadConn();
        // Connection con = DruidUtils.getInstance().getConnection();
        String sql = "select * from account where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while(resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
        // con.close();

        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {

        // 从连接池获取连接 => query the bound connection from the current thread
        Connection con = connectionUtils.getCurrentThreadConn();
        // Connection con = DruidUtils.getInstance().getConnection();
        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        // DO NOT close the connection! otherwise it will remove the threadloal!
        // con.close();
        return i;
    }
}
