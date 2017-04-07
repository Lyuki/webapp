package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Poll;
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
public class PollRepositoryImpl implements PollRepository {
    
    private DataSource dataSource;
    private JdbcOperations jdbcOp;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

  @Override
  public List<Poll> findAll() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
    
    private static final class PollRowMapper implements RowMapper<Poll> {

        @Override
        public Poll mapRow(ResultSet rs, int i) throws SQLException {
            Poll poll = new Poll();
            poll.setQuestion(rs.getString("question"));
            poll.setAns1(rs.getString("ans1"));
            poll.setAns2(rs.getString("ans2"));
            poll.setAns3(rs.getString("ans3"));
            poll.setAns4(rs.getString("ans4"));
            return poll;
        }
    }
    
    private static final String SQL_INSERT_POLL
            = "insert into poll (question, ans1, ans2, ans3, ans4) values (?, ?, ?, ?, ?)";
    
    @Override
    public void create(Poll poll) {
        jdbcOp.update(SQL_INSERT_POLL,
                poll.getQuestion(),
                poll.getAns1(),
                poll.getAns2(),
                poll.getAns3(),
                poll.getAns4());
    }
    
    private static final String SQL_SELECT_POLL
            = "select question, ans1, ans2, ans3, ans4 from poll where question = ?";
    
    @Override
    public Poll findByPollQ(String question) {
        Poll poll = jdbcOp.queryForObject(SQL_SELECT_POLL,
                new PollRowMapper(), question);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_POLL,
                question);

        return poll;
    }
    
    
    
}
