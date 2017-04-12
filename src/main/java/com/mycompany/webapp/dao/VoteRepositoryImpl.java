package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Vote;
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

public class VoteRepositoryImpl implements VoteRepository {
    
    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }
    
    private static final class VoteRowMapper implements RowMapper<Vote> {

        @Override
        public Vote mapRow(ResultSet rs, int i) throws SQLException {
            Vote vote = new Vote();
            vote.setPollId(rs.getLong("poll_id"));
            vote.setCustomerName(rs.getString("username"));
            vote.setAnsId(rs.getLong("ans_id"));
            return vote;
        }
    }
    
    private static final String SQL_INSERT_VOTE
            = "insert into vote (poll_id, username, ans_id) values (?, ?, ?)";
    
    @Override
        public void create(Vote vote) {
            jdbcOp.update(SQL_INSERT_VOTE,
                    vote.getPollId(),
                    vote.getCustomerName(),
                    vote.getAnsId()
            );
        }
        
    private static final String SQL_SELECT_ALL_VOTE
            = "select * from vote";
    
    @Override
        public List<Vote> findAll() {
            List<Vote> vote = new ArrayList<>();
            List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_VOTE);

            for (Map<String, Object> row : rows) {
                Vote votes = new Vote();
                long vid = (int) row.get("id");
                votes.setId(vid);
                votes.setPollId((Long) row.get("poll_id"));
                votes.setCustomerName((String) row.get("username"));
                votes.setAnsId((Long) row.get("ans_id"));
                vote.add(votes);
            }
            return vote;
        }
        
    private static final String SQL_SELECT_VOTE
            = "select * from vote where poll_id = ?";
    
    @Override
        public Vote findByPollID(long id) {
            Vote vote = jdbcOp.queryForObject(SQL_SELECT_VOTE,
                    new VoteRowMapper(), id);
            return vote;
        }
}
