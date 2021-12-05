package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountsDao implements AccountsDao{

    private JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getBalance(int id) {
        BigDecimal balance = null;
        String sql = "SELECT balance \n" +
                "FROM accounts \n" +
                "JOIN users ON users.user_id = accounts.user_id\n" +
                "WHERE users.user_id = ?;";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},BigDecimal.class);

    }

}
