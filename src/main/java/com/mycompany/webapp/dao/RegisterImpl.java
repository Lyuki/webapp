package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterImpl implements Register {

  DataSource dataSource;
  private final JdbcOperations jdbcOp;

  @Autowired
  public RegisterImpl(DataSource dataSource) {
    this.dataSource = dataSource;
    jdbcOp = new JdbcTemplate(this.dataSource);
  }

  private static final String SQL_INSERT_ENTRY
          = "insert into users (username, password) values (?, ?)";

  @Override
  public void create(User entry) {
    jdbcOp.update(SQL_INSERT_ENTRY,
            entry.getUsername(),
            entry.getPassword()
    );
  }

  private static final String SQL_SELECT_ENTRY
          = "select username, password from users where username = ?";

  @Override
  public User findById(int id) {
    return jdbcOp.queryForObject(SQL_SELECT_ENTRY, new EntryRowMapper(), id);
  }

  private static final class EntryRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
      User entry = new User();
      entry.setUsername(rs.getString("username"));
      entry.setPassword(rs.getString("password"));
      return entry;
    }
  }
}
