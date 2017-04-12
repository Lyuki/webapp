package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Result;
import com.mycompany.webapp.model.Vote;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            vote.setId(rs.getLong("id"));
            vote.setPollId(rs.getLong("poll_id"));
            vote.setCustomerName(rs.getString("username"));
            vote.setAnsId(rs.getLong("ans_id"));
            return vote;
        }
    }
    
    private static final class ResultRowMapper implements RowMapper<Result> {

        @Override
        public Result mapRow(ResultSet rs, int i) throws SQLException {
            Result result = new Result();
            if (rs.getString("result1") == null) {
                result.setResult1(0);
            }else {
            result.setResult1(rs.getLong("result1"));
            }
            if (rs.getString("result2") == null) {
                result.setResult2(0);
            }else {
            result.setResult2(rs.getLong("result2"));
            }
            if (rs.getString("result3") == null) {
                result.setResult3(0);
            }else {
            result.setResult3(rs.getLong("result3"));
            }
            if (rs.getString("result4") == null) {
                result.setResult4(0);
            }else {
            result.setResult4(rs.getLong("result4"));
            }
            
            result.setTitle(rs.getString("question"));
            result.setAns1(rs.getString("ans1"));
            result.setAns2(rs.getString("ans2"));
            result.setAns3(rs.getString("ans3"));
            result.setAns4(rs.getString("ans4"));
            result.setPollId(rs.getLong("id"));
            return result;
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
            = "select * from vote where poll_id = ? and username = ?";

    @Override
    public Vote findByPollID(long id, String username) {
        try {
            Vote vote = jdbcOp.queryForObject(SQL_SELECT_VOTE,
                    new VoteRowMapper(), id, username);
            return vote;
        } catch (EmptyResultDataAccessException e) {
            return new Vote();
        }
    }
    
    private static final String SQL_SELECT_NO_OF_VOTE
            = "select id,question,ans1,ans2,ans3,ans4, (select count(*) from vote where poll_id = ? and ans_id = 1 group by poll_id, ans_id) as result1,\n" +
"(select count(*) from vote where poll_id = ? and ans_id = 2 group by poll_id, ans_id) as result2,\n" +
"(select count(*) from vote where poll_id = ? and ans_id = 3 group by poll_id, ans_id) as result3,\n" +
"(select count(*) from vote where poll_id = ? and ans_id = 4 group by poll_id, ans_id) as result4\n" +
"from POLL where id = ?";

    @Override
    public Result findVoteByPollID(long pollId) {
        try {
            Result result = jdbcOp.queryForObject(SQL_SELECT_NO_OF_VOTE,
                    new ResultRowMapper(), pollId, pollId, pollId, pollId, pollId);
            return result;
        } catch (EmptyResultDataAccessException e) {
            return new Result();
        }
    }
}
