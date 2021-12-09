package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listOfTransfers() {
        return null;
    }

//    @Override
//    public boolean create(Transfer transfer) {
//
//        // create user
//        String sql = "INSERT INTO transfers (account_from, account_to, amount) VALUES (?, ?) RETURNING transfer_id";
////        String password_hash = new BCryptPasswordEncoder().encode(password);
//        Integer newTransferId;
//        try {
//            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, account_from, account_to, amount);
//        } catch (DataAccessException e) {
//            return false;
//        }
//
//        // create account
//        sql = "INSERT INTO accounts (user_id, balance) values(?, ?)";
//        try {
//            jdbcTemplate.update(sql, newTransferId, STARTING_BALANCE);
//        } catch (DataAccessException e) {
//            return false;
//        }
//
//        return true;    }

    @Override
    public boolean createTransfer(Transfer transfer){
//        public Transfer createTransfer(long from_user_id, long to_user_id, BigDecimal amount){

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (2, 2, (SELECT account_id from accounts WHERE user_id = ?) , (SELECT account_id from accounts WHERE user_id = ?), ?) RETURNING transfer_id ";

        long newTransferId = -1;

        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Long.class, transfer);

            System.out.println(newTransferId);
        }catch (DataAccessException e) {
            System.out.println("Transfer creation failed");
        }
        return true;
    }



}
