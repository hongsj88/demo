package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements org.springframework.jdbc.core.RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int i) throws SQLException {
//        Customer customer = new Customer();
//        customer.setId(rs.getInt("id"));
//        customer.setFirstName(rs.getString("firstName"));
//        customer.setLastName(rs.getString("lastName"));
//        customer.setBirthdate(rs.getString("brithdate"));
//
//        return customer;

        return new Customer(rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("birthdate"));

    }
}
