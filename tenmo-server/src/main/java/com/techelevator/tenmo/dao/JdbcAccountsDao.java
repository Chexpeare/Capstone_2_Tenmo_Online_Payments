package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcAccountsDao implements AccountsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account getBalance(int id) {

        String sql = "SELECT account_id, users.user_id, balance " +
                "FROM accounts " +
                "JOIN users ON users.user_id = accounts.user_id " +
                "WHERE users.user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        Account account = null;
        while(results.next()) {
            account = mapRowToAccount(results);
            System.out.println(account);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account acctData = new Account();
        acctData.setAccountId(results.getLong("account_id"));
        acctData.setUserId(results.getLong("user_id"));
        acctData.setBalance(results.getDouble("balance"));
        return acctData;
    }

}