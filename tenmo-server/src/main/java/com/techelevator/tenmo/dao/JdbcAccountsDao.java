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
        Account account = null;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while(results.next()) {
            account = mapRowToAccount(results);
            System.out.println(account);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account cc = new Account();
        cc.setAccountId(rs.getLong("account_id"));
        cc.setUserId(rs.getLong("user_id"));
        cc.setBalance(rs.getDouble("balance"));
        return cc;
    }

}