package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDao implements AccountsDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(long user_id){

        Account account = null;

        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts " +
                "WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public void addToAccountBalance(BigDecimal amount, long user_id){

        String sql = "UPDATE accounts " +
                "SET balance = balance + ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, amount, user_id);
    }

    @Override
    public void subtractFromAccountBalance(BigDecimal amount,long user_id){

        String sql = "UPDATE accounts " +
                "SET balance = balance - ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, amount, user_id);
    };

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }

}