package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.AllUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final String SQL_INSERT_USER
            = "insert into users (username, password) values (?, ?)";

    private static final String SQL_INSERT_ROLE
            = "insert into user_roles (username, role) values (?, ?)";

    @Override
    public void create(AllUser user) {

        jdbcOp.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getPassword());

        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE,
                    user.getUsername(),
                    role);
        }

    }

    private static final class UserRowMapper implements RowMapper<AllUser> {

        @Override
        public AllUser mapRow(ResultSet rs, int i) throws SQLException {
            AllUser user = new AllUser();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }

    private static final String SQL_SELECT_USER
            = "select username, password from users where username = ?";
    private static final String SQL_SELECT_ROLES
            = "select username, role from user_roles where username = ?";

    @Override

    public AllUser findByUsername(String username) {

        AllUser ticketUser = jdbcOp.queryForObject(SQL_SELECT_USER,
                new UserRowMapper(), username);

        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES,
                username);

        for (Map<String, Object> row : rows) {

            ticketUser.addRole((String) row.get("role"));

        }

        return ticketUser;

    }
}