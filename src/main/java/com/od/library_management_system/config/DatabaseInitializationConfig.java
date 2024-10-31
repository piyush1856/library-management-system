package com.od.library_management_system.config;

import com.od.library_management_system.utils.TranSqlServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializationConfig {

    private final TranSqlServices tranSqlServices;

    @Autowired
    public DatabaseInitializationConfig(TranSqlServices tranSqlServices) {
        this.tranSqlServices = tranSqlServices;
    }

    @PostConstruct
    public void initializeDatabase() {
        createBooksTable();
        createMembersTable();
        createBorrowTable();
    }

    private void createBooksTable() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS book (")
                .append("id SERIAL PRIMARY KEY,")
                .append("title VARCHAR(255) NOT NULL,")
                .append("author VARCHAR(255) NOT NULL,")
                .append("isbn VARCHAR(20) NOT NULL UNIQUE,")
                .append("published_date DATE NOT NULL,")
                .append("available_copies INT NOT NULL)");

        String sql = sqlBuilder.toString();
        tranSqlServices.execute(sql);
    }

    private void createMembersTable() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS member (")
                .append("id SERIAL PRIMARY KEY,")
                .append("name VARCHAR(255) NOT NULL,")
                .append("phone VARCHAR(15) NOT NULL,")
                .append("registered_date DATE NOT NULL)");

        String sql = sqlBuilder.toString();
        tranSqlServices.execute(sql);
    }

    private void createBorrowTable() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS borrow (")
                .append("id SERIAL PRIMARY KEY,")
                .append("member_id INT NOT NULL,")
                .append("book_id INT NOT NULL,")
                .append("borrowed_date DATE NOT NULL,")
                .append("due_date DATE NOT NULL,")
                .append("FOREIGN KEY (member_id) REFERENCES member(id),")
                .append("FOREIGN KEY (book_id) REFERENCES book(id))");

        String sql = sqlBuilder.toString();
        tranSqlServices.execute(sql);
    }

}
