package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Reply;
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

public class ReplyRepositoryImpl implements ReplyRepository{
    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class ReplyRowMapper implements RowMapper<Reply> {

        @Override
        public Reply mapRow(ResultSet rs, int i) throws SQLException {
            Reply reply = new Reply();
            //topic.setId(rs.getLong("id"));
            reply.setMsg(rs.getString("msg"));
            reply.setTopicId(rs.getInt("topic_id"));
            reply.setCustomerName(rs.getString("username"));
            return reply;
        }
    }

    private static final String SQL_INSERT_REPLY
            = "insert into reply (msg, topic_id, username) values (?, ?, ?)";
    private static final String SQL_INSERT_ATTACH
            = "insert into attachment (topic_id, reply_id, username, attachment) values ( ?,(SELECT MAX(ID) FROM REPLY), ?, ?)";

    @Override
    public void create(Reply reply) {
        jdbcOp.update(SQL_INSERT_REPLY,  
                reply.getMsg(),
                reply.getTopicId(),
                reply.getCustomerName()
        );

        for (Attachment attach : reply.getAttachments()) {
            jdbcOp.update(SQL_INSERT_ATTACH,
                    0,
                    reply.getCustomerName(),
                    attach);
        }
    }

    private static final String SQL_SELECT_ALL_REPLY
            = "select * from reply";
    private static final String SQL_SELECT_ATTACH
            = "select attachment from attachment where reply_id = ?";

    @Override
    public List<Reply> findAll() {
        List<Reply> replys = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY);

        for (Map<String, Object> row : rows) {
            Reply reply = new Reply();
            long tid = (int) row.get("id");
            reply.setId(tid);
            reply.setMsg((String) row.get("msg"));
            reply.setTopicId((int) row.get("topic_id"));
            reply.setCustomerName((String) row.get("username"));
            List<Map<String, Object>> roleRows = jdbcOp.queryForList(SQL_SELECT_ATTACH, tid);
            for (Map<String, Object> roleRow : roleRows) {
                reply.addAttachment((Attachment) roleRow.get("attachment"));
            }
            replys.add(reply);
        }
        return replys;
    }

    private static final String SQL_SELECT_REPLY
            = "select * from reply where topic_id = ?";

    @Override
    public Reply findByID(long id) {
        Reply reply = jdbcOp.queryForObject(SQL_SELECT_REPLY,
                new ReplyRowMapper(), id);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ATTACH,
                id);

        for (Map<String, Object> row : rows) {
            reply.addAttachment((Attachment) row.get("attachment"));
        }
        return reply;
    }

    private static final String SQL_DELETE_REPLY
            = "delete from reply where id = ?";
    private static final String SQL_DELETE_ATTACH
            = "delete from attachment where reply_id = ?";

    @Override
    public void deleteByID(long id) {
        jdbcOp.update(SQL_DELETE_ATTACH, id);
        jdbcOp.update(SQL_DELETE_REPLY, id);
    }
    
    private static final String SQL_UPDATE_REPLY
            = "update reply set msg = ? , topic_id = ?, username = ? where id = ?";
    private static final String SQL_UPDATE_ATTACH
            = "update attachment set username = ?, attachment = ? where reply_id = ?";
    
    @Override
    public void editByID(Reply reply) {
        jdbcOp.update(SQL_UPDATE_REPLY,
                reply.getMsg(),
                reply.getTopicId(),
                reply.getCustomerName(),
                reply.getId()
        );

        for (Attachment attach : reply.getAttachments()) {
            jdbcOp.update(SQL_UPDATE_ATTACH,
                    reply.getCustomerName(),
                    attach,
                    reply.getId()
                    );
        }
    }
}
