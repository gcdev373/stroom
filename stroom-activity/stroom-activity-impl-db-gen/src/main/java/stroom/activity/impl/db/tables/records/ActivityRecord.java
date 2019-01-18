/*
 * This file is generated by jOOQ.
 */
package stroom.activity.impl.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;

import stroom.activity.impl.db.tables.Activity;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActivityRecord extends UpdatableRecordImpl<ActivityRecord> implements Record8<Integer, Integer, Long, String, Long, String, String, String> {

    private static final long serialVersionUID = -1734083532;

    /**
     * Setter for <code>stroom.activity.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stroom.activity.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stroom.activity.version</code>.
     */
    public void setVersion(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>stroom.activity.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>stroom.activity.create_time_ms</code>.
     */
    public void setCreateTimeMs(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>stroom.activity.create_time_ms</code>.
     */
    public Long getCreateTimeMs() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>stroom.activity.create_user</code>.
     */
    public void setCreateUser(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stroom.activity.create_user</code>.
     */
    public String getCreateUser() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stroom.activity.update_time_ms</code>.
     */
    public void setUpdateTimeMs(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>stroom.activity.update_time_ms</code>.
     */
    public Long getUpdateTimeMs() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>stroom.activity.update_user</code>.
     */
    public void setUpdateUser(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>stroom.activity.update_user</code>.
     */
    public String getUpdateUser() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stroom.activity.user_id</code>.
     */
    public void setUserId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>stroom.activity.user_id</code>.
     */
    public String getUserId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stroom.activity.json</code>.
     */
    public void setJson(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>stroom.activity.json</code>.
     */
    public String getJson() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Long, String, Long, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Long, String, Long, String, String, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Activity.ACTIVITY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Activity.ACTIVITY.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return Activity.ACTIVITY.CREATE_TIME_MS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Activity.ACTIVITY.CREATE_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Activity.ACTIVITY.UPDATE_TIME_MS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Activity.ACTIVITY.UPDATE_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Activity.ACTIVITY.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Activity.ACTIVITY.JSON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getCreateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCreateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getUpdateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUpdateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getJson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getCreateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCreateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getUpdateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUpdateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getJson();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value2(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value3(Long value) {
        setCreateTimeMs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value4(String value) {
        setCreateUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value5(Long value) {
        setUpdateTimeMs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value6(String value) {
        setUpdateUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value7(String value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord value8(String value) {
        setJson(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityRecord values(Integer value1, Integer value2, Long value3, String value4, Long value5, String value6, String value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivityRecord
     */
    public ActivityRecord() {
        super(Activity.ACTIVITY);
    }

    /**
     * Create a detached, initialised ActivityRecord
     */
    public ActivityRecord(Integer id, Integer version, Long createTimeMs, String createUser, Long updateTimeMs, String updateUser, String userId, String json) {
        super(Activity.ACTIVITY);

        set(0, id);
        set(1, version);
        set(2, createTimeMs);
        set(3, createUser);
        set(4, updateTimeMs);
        set(5, updateUser);
        set(6, userId);
        set(7, json);
    }
}
