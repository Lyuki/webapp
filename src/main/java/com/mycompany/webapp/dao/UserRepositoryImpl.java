package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.AllUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        this.jdbcOp = new JdbcTemplate(this.dataSource);
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

    private static final String SQL_INSERT_USER
            = "insert into users (username, password) values (?, ?)";
    private static final String SQL_INSERT_ROLE
            = "insert into user_roles (username, role) values (?, ?)";

    @Override
    public void create(AllUser user) {
        jdbcOp.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getPassword());

        jdbcOp.update(SQL_INSERT_ROLE,
                user.getUsername(),
                user.getRoles());
    }

    private static final String SQL_SELECT_ALL_USER
            = "select username, password from users";
    private static final String SQL_SELECT_ROLES
            = "select username, role from user_roles where username = ?";

    @Override
    public List<AllUser> findAll() {
        List<AllUser> users = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_USER);

        for (Map<String, Object> row : rows) {
            AllUser user = new AllUser();
            String username = (String) row.get("username");
            user.setUsername(username);
            user.setPassword((String) row.get("password"));
            List<Map<String, Object>> roleRows = jdbcOp.queryForList(SQL_SELECT_ROLES, username);
            for (Map<String, Object> roleRow : roleRows) {
                user.addRole((String) roleRow.get("role"));
            }
            users.add(user);
        }
        return users;
    }

    private static final String SQL_SELECT_USER
            = "select username, password from users where username = ?";

    @Override
    public AllUser findByUsername(String username) {
        AllUser ticketUser = jdbcOp.queryForObject(SQL_SELECT_USER, new UserRowMapper(), username);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES, username);
        for (Map<String, Object> row : rows) {
            ticketUser.addRole((String) row.get("role"));
        }
        return ticketUser;
    }

    private static final String SQL_EDIT_ROLE
            = "update user_roles set role = ? where username = ?";
    private static final String SQL_EDIT_USER
            = "update users set password = ? where username = ?";
    private static final String SQL_SELECT_ROLES_ONLY
            = "select role from user_roles where username = ?";

    @Override
    public void editUser(AllUser editUser) {
        if (!editUser.getPassword().equals("")) {
            jdbcOp.update(SQL_EDIT_USER,
                    editUser.getPassword(),
                    editUser.getUsername());
        }
        if (!editUser.getRoles().equals("")) {
            jdbcOp.update(SQL_EDIT_ROLE, editUser.getRoles(), editUser.getUsername());
        } else {
            jdbcOp.update(SQL_EDIT_ROLE, SQL_SELECT_ROLES_ONLY, editUser.getUsername());
        }
    }

    private static final String SQL_DELETE_USER
            = "delete from users where username = ?";
    private static final String SQL_DELETE_ROLES
            = "delete from user_roles where username = ?";

    @Override
    public void deleteByUsername(String username) {
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }
}
