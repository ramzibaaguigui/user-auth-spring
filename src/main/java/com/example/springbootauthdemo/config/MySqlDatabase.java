package com.example.springbootauthdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class MySqlDatabase {


    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://www.mohamedelfateh.com:3306/cpsess1342389225/3rdparty/phpMyAdmin/db_structure.php?server=1&db=mohelfat_microhack");
        dataSource.setUsername("beraoudabdelkhalek");
        dataSource.setPassword("dzhoster2023@");
        return dataSource;
    }
}
