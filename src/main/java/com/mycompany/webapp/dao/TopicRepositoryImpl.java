package com.mycompany.webapp.dao;

import com.mycompany.webapp.model.Attachment;
import com.mycompany.webapp.model.Topics;
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

public class TopicRepositoryImpl implements TopicRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class TopicRowMapper implements RowMapper<Topics> {

        @Override
        public Topics mapRow(ResultSet rs, int i) throws SQLException {
            Topics topic = new Topics();
            //topic.setId(rs.getLong("id"));
            topic.setTitle(rs.getString("title"));
            topic.setMsg(rs.getString("msg"));
            topic.setCategory(rs.getString("category"));
            topic.setCustomerName(rs.getString("username"));
            return topic;
        }
    }
    
    private static final class AttachRowMapper implements RowMapper<Attachment> {

        @Override
        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment attach = new Attachment();
            attach.setContents(rs.getBytes("content"));
            attach.setMimeContentType(rs.getString("content_type"));
            attach.setName(rs.getString("filename"));
            return attach;
        }
    }

    private static final String SQL_INSERT_TOPIC
            = "insert into topic (title, msg, category, username) values (?, ?, ?, ?)";
    private static final String SQL_INSERT_ATTACH
            = "insert into attachment (topic_id, reply_id, username, filename, content_type, content) values ((SELECT MAX(ID) FROM TOPIC), ?, ?, ?, ?, ?)";

    @Override
    public void create(Topics topic) {
        jdbcOp.update(SQL_INSERT_TOPIC,
                topic.getTitle(),
                topic.getMsg(),
                topic.getCategory(),
                topic.getCustomerName()
        );

        for (Attachment attach : topic.getAttachments()) {
            jdbcOp.update(SQL_INSERT_ATTACH,
                    0,
                    topic.getCustomerName(),
                    attach.getName(),
                    attach.getMimeContentType(),
                    attach.getContents());
        }
    }

    private static final String SQL_SELECT_ALL_TOPIC
            = "select * from topic where category = ?";
    private static final String SQL_SELECT_ATTACH
            = "select filename, content_type, content from attachment where topic_id = ?";

    @Override
    public List<Topics> findAll(String cate) {
        List<Topics> topics = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_TOPIC, cate);

        for (Map<String, Object> row : rows) {
            Topics topic = new Topics();
            long tid = (int) row.get("id");
            topic.setId(tid);
            topic.setTitle((String) row.get("title"));
            topic.setMsg((String) row.get("msg"));
            topic.setCategory((String) row.get("category"));
            topic.setCustomerName((String) row.get("username"));
            List<Map<String, Object>> roleRows = jdbcOp.queryForList(SQL_SELECT_ATTACH, tid);
            for (Map<String, Object> roleRow : roleRows) {
                Attachment attach = new Attachment();
                attach.setContents((byte[])roleRow.get("content"));
                attach.setMimeContentType((String)roleRow.get("content_type"));
                attach.setName((String)roleRow.get("filename"));
                topic.addAttachment(attach);
            }
            topics.add(topic);
        }
        return topics;
    }

    private static final String SQL_SELECT_TOPIC
            = "select * from topic where id = ?";

    @Override
    public Topics findByID(long id) {
        Topics topic = jdbcOp.queryForObject(SQL_SELECT_TOPIC,
                new TopicRowMapper(), id);
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ATTACH,
                id);

        for (Map<String, Object> row : rows) {
            Attachment attach = new Attachment();
                attach.setContents((byte[])row.get("content"));
                attach.setMimeContentType((String)row.get("content_type"));
                attach.setName((String)row.get("filename"));
            topic.addAttachment(attach);
        }
        return topic;
    }
    
    private static final String SQL_SELECT_ATTACH_BYID
            = "select * from attachment where topic_id = ? and filename = ?";

    @Override
    public Attachment findAttachByID(long id, String filename) {
        Attachment attach = jdbcOp.queryForObject(SQL_SELECT_ATTACH_BYID,
                new AttachRowMapper(),id, filename);
        return attach;
    }

    private static final String SQL_DELETE_TOPIC
            = "delete from topic where id = ?";
    private static final String SQL_DELETE_ATTACH
            = "delete from attachment where topic_id = ?";
    private static final String SQL_DELETE_REPLY
            = "delete from reply where topic_id = ?";

    @Override
    public void deleteByID(long id) {
        jdbcOp.update(SQL_DELETE_ATTACH, id);
        jdbcOp.update(SQL_DELETE_TOPIC, id);
        jdbcOp.update(SQL_DELETE_REPLY, id);
    }
    
    private static final String SQL_UPDATE_TOPIC
            = "update topic set title = ?, msg = ? , category = ?, username = ? where id = ?";
    private static final String SQL_UPDATE_ATTACH
            = "update attachment set username = ?, filename = ?, content_type = ?, content = ? where topic_id = ?";
    
    @Override
    public void editByID(Topics topic) {
        jdbcOp.update(SQL_UPDATE_TOPIC,
                topic.getTitle(),
                topic.getMsg(),
                topic.getCategory(),
                topic.getCustomerName(),
                topic.getId()
        );

        for (Attachment attach : topic.getAttachments()) {
            jdbcOp.update(SQL_UPDATE_ATTACH,
                    topic.getCustomerName(),
                    attach.getName(),
                    attach.getMimeContentType(),
                    attach.getContents(),
                    topic.getId()
                    );
        }
    }
}
