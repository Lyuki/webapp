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
            = "insert into attachment (topic_id, reply_id, username, filename, content_type, content) values (?,(SELECT MAX(ID) FROM REPLY),  ?, ?, ?, ?)";

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
                    attach.getName(),
                    attach.getMimeContentType(),
                    attach.getContents());
        }
    }

    private static final String SQL_SELECT_ALL_REPLY
            = "select * from reply where topic_id = ?";
    private static final String SQL_SELECT_ATTACH
            = "select filename, content, content_type from attachment where reply_id = ?";

    @Override
    public List<Reply> findAll(long topic_id) {
        List<Reply> replys = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, topic_id);

        for (Map<String, Object> row : rows) {
            Reply reply = new Reply();
            long tid = (int) row.get("id");
            reply.setId(tid);
            reply.setMsg((String) row.get("msg"));
            reply.setTopicId((int) row.get("topic_id"));
            reply.setCustomerName((String) row.get("username"));
            List<Map<String, Object>> roleRows = jdbcOp.queryForList(SQL_SELECT_ATTACH, tid);
            for (Map<String, Object> roleRow : roleRows) {
                Attachment attach = new Attachment();
                attach.setContents((byte[])roleRow.get("content"));
                attach.setMimeContentType((String)roleRow.get("content_type"));
                attach.setName((String)roleRow.get("filename"));
                reply.addAttachment(attach);
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
            Attachment attach = new Attachment();
                attach.setContents((byte[])row.get("content"));
                attach.setMimeContentType((String)row.get("content_type"));
                attach.setName((String)row.get("filename"));
                reply.addAttachment(attach);
        }
        return reply;
    }
    
    private static final String SQL_SELECT_ATTACH_BYID
            = "select * from attachment where reply_id = ? and filename = ?";

    @Override
    public Attachment findAttachByID(long id, String filename) {
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ATTACH_BYID,
                id,filename);
        Attachment attach = new Attachment();
        for (Map<String, Object> row : rows) {        
                attach.setContents((byte[])row.get("content"));
                attach.setMimeContentType((String)row.get("content_type"));
                attach.setName((String)row.get("filename"));           
        }
        return attach;
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
            = "update attachment set username = ?, filename = ?, content_type = ?, content = ? where reply_id = ?";
    
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
                    attach.getName(),
                    attach.getMimeContentType(),
                    attach.getContents(),
                    reply.getId()
                    );
        }
    }
}
