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

    private static final String SQL_INSERT_TOPIC
            = "insert into topic (title, msg, category, username) values (?, ?, ?, ?)";
    private static final String SQL_INSERT_ATTACH
            = "insert into attachment (topic_id, reply_id, username, attachment) values ((SELECT MAX(ID) FROM TOPIC), ?, ?, ?)";

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
                    attach);
        }
    }

    private static final String SQL_SELECT_ALL_TOPIC
            = "select * from topic";
    private static final String SQL_SELECT_ATTACH
            = "select attachment from attachment where topic_id = ?";

    @Override
    public List<Topics> findAll() {
        List<Topics> topics = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_TOPIC);

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
                topic.addAttachment((Attachment) roleRow.get("attachment"));
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
            topic.addAttachment((Attachment) row.get("attachment"));
        }
        return topic;
    }

    private static final String SQL_DELETE_TOPIC
            = "delete from users where username = ?";
    private static final String SQL_DELETE_ATTACH
            = "delete from user_roles where username = ?";

    @Override
    public void deleteByID(long id) {
        jdbcOp.update(SQL_DELETE_ATTACH, id);
        jdbcOp.update(SQL_DELETE_TOPIC, id);
    }
    
    private static final String SQL_UPDATE_TOPIC
            = "update topic set title = ?, msg = ? , category = ?, username = ? where id = ?";
    private static final String SQL_UPDATE_ATTACH
            = "update attachment set username = ?, attachment = ? where topic_id = ?";
    
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
                    attach,
                    topic.getId()
                    );
        }
    }
}
