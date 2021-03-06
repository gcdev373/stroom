/*
 * This file is generated by jOOQ.
 */
package stroom.authentication.impl.db.jooq.tables.records;


import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;

import stroom.authentication.impl.db.jooq.tables.Account;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record22<Integer, Integer, Long, String, Long, String, String, String, Long, String, String, String, Integer, Integer, Long, Long, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> {

    private static final long serialVersionUID = 666189256;

    /**
     * Setter for <code>stroom.account.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stroom.account.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stroom.account.version</code>.
     */
    public void setVersion(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>stroom.account.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>stroom.account.create_time_ms</code>.
     */
    public void setCreateTimeMs(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>stroom.account.create_time_ms</code>.
     */
    public Long getCreateTimeMs() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>stroom.account.create_user</code>.
     */
    public void setCreateUser(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stroom.account.create_user</code>.
     */
    public String getCreateUser() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stroom.account.update_time_ms</code>.
     */
    public void setUpdateTimeMs(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>stroom.account.update_time_ms</code>.
     */
    public Long getUpdateTimeMs() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>stroom.account.update_user</code>.
     */
    public void setUpdateUser(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>stroom.account.update_user</code>.
     */
    public String getUpdateUser() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stroom.account.email</code>.
     */
    public void setEmail(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>stroom.account.email</code>.
     */
    public String getEmail() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stroom.account.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>stroom.account.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(7);
    }

    /**
     * Setter for <code>stroom.account.password_last_changed_ms</code>.
     */
    public void setPasswordLastChangedMs(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>stroom.account.password_last_changed_ms</code>.
     */
    public Long getPasswordLastChangedMs() {
        return (Long) get(8);
    }

    /**
     * Setter for <code>stroom.account.first_name</code>.
     */
    public void setFirstName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>stroom.account.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>stroom.account.last_name</code>.
     */
    public void setLastName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>stroom.account.last_name</code>.
     */
    public String getLastName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>stroom.account.comments</code>.
     */
    public void setComments(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>stroom.account.comments</code>.
     */
    public String getComments() {
        return (String) get(11);
    }

    /**
     * Setter for <code>stroom.account.login_count</code>.
     */
    public void setLoginCount(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>stroom.account.login_count</code>.
     */
    public Integer getLoginCount() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>stroom.account.login_failures</code>.
     */
    public void setLoginFailures(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>stroom.account.login_failures</code>.
     */
    public Integer getLoginFailures() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>stroom.account.last_login_ms</code>.
     */
    public void setLastLoginMs(Long value) {
        set(14, value);
    }

    /**
     * Getter for <code>stroom.account.last_login_ms</code>.
     */
    public Long getLastLoginMs() {
        return (Long) get(14);
    }

    /**
     * Setter for <code>stroom.account.reactivated_ms</code>.
     */
    public void setReactivatedMs(Long value) {
        set(15, value);
    }

    /**
     * Getter for <code>stroom.account.reactivated_ms</code>.
     */
    public Long getReactivatedMs() {
        return (Long) get(15);
    }

    /**
     * Setter for <code>stroom.account.force_password_change</code>.
     */
    public void setForcePasswordChange(Boolean value) {
        set(16, value);
    }

    /**
     * Getter for <code>stroom.account.force_password_change</code>.
     */
    public Boolean getForcePasswordChange() {
        return (Boolean) get(16);
    }

    /**
     * Setter for <code>stroom.account.never_expires</code>.
     */
    public void setNeverExpires(Boolean value) {
        set(17, value);
    }

    /**
     * Getter for <code>stroom.account.never_expires</code>.
     */
    public Boolean getNeverExpires() {
        return (Boolean) get(17);
    }

    /**
     * Setter for <code>stroom.account.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(18, value);
    }

    /**
     * Getter for <code>stroom.account.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(18);
    }

    /**
     * Setter for <code>stroom.account.inactive</code>.
     */
    public void setInactive(Boolean value) {
        set(19, value);
    }

    /**
     * Getter for <code>stroom.account.inactive</code>.
     */
    public Boolean getInactive() {
        return (Boolean) get(19);
    }

    /**
     * Setter for <code>stroom.account.locked</code>.
     */
    public void setLocked(Boolean value) {
        set(20, value);
    }

    /**
     * Getter for <code>stroom.account.locked</code>.
     */
    public Boolean getLocked() {
        return (Boolean) get(20);
    }

    /**
     * Setter for <code>stroom.account.processing_account</code>.
     */
    public void setProcessingAccount(Boolean value) {
        set(21, value);
    }

    /**
     * Getter for <code>stroom.account.processing_account</code>.
     */
    public Boolean getProcessingAccount() {
        return (Boolean) get(21);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record22 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row22<Integer, Integer, Long, String, Long, String, String, String, Long, String, String, String, Integer, Integer, Long, Long, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    @Override
    public Row22<Integer, Integer, Long, String, Long, String, String, String, Long, String, String, String, Integer, Integer, Long, Long, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean> valuesRow() {
        return (Row22) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Account.ACCOUNT.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Account.ACCOUNT.VERSION;
    }

    @Override
    public Field<Long> field3() {
        return Account.ACCOUNT.CREATE_TIME_MS;
    }

    @Override
    public Field<String> field4() {
        return Account.ACCOUNT.CREATE_USER;
    }

    @Override
    public Field<Long> field5() {
        return Account.ACCOUNT.UPDATE_TIME_MS;
    }

    @Override
    public Field<String> field6() {
        return Account.ACCOUNT.UPDATE_USER;
    }

    @Override
    public Field<String> field7() {
        return Account.ACCOUNT.EMAIL;
    }

    @Override
    public Field<String> field8() {
        return Account.ACCOUNT.PASSWORD_HASH;
    }

    @Override
    public Field<Long> field9() {
        return Account.ACCOUNT.PASSWORD_LAST_CHANGED_MS;
    }

    @Override
    public Field<String> field10() {
        return Account.ACCOUNT.FIRST_NAME;
    }

    @Override
    public Field<String> field11() {
        return Account.ACCOUNT.LAST_NAME;
    }

    @Override
    public Field<String> field12() {
        return Account.ACCOUNT.COMMENTS;
    }

    @Override
    public Field<Integer> field13() {
        return Account.ACCOUNT.LOGIN_COUNT;
    }

    @Override
    public Field<Integer> field14() {
        return Account.ACCOUNT.LOGIN_FAILURES;
    }

    @Override
    public Field<Long> field15() {
        return Account.ACCOUNT.LAST_LOGIN_MS;
    }

    @Override
    public Field<Long> field16() {
        return Account.ACCOUNT.REACTIVATED_MS;
    }

    @Override
    public Field<Boolean> field17() {
        return Account.ACCOUNT.FORCE_PASSWORD_CHANGE;
    }

    @Override
    public Field<Boolean> field18() {
        return Account.ACCOUNT.NEVER_EXPIRES;
    }

    @Override
    public Field<Boolean> field19() {
        return Account.ACCOUNT.ENABLED;
    }

    @Override
    public Field<Boolean> field20() {
        return Account.ACCOUNT.INACTIVE;
    }

    @Override
    public Field<Boolean> field21() {
        return Account.ACCOUNT.LOCKED;
    }

    @Override
    public Field<Boolean> field22() {
        return Account.ACCOUNT.PROCESSING_ACCOUNT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getVersion();
    }

    @Override
    public Long component3() {
        return getCreateTimeMs();
    }

    @Override
    public String component4() {
        return getCreateUser();
    }

    @Override
    public Long component5() {
        return getUpdateTimeMs();
    }

    @Override
    public String component6() {
        return getUpdateUser();
    }

    @Override
    public String component7() {
        return getEmail();
    }

    @Override
    public String component8() {
        return getPasswordHash();
    }

    @Override
    public Long component9() {
        return getPasswordLastChangedMs();
    }

    @Override
    public String component10() {
        return getFirstName();
    }

    @Override
    public String component11() {
        return getLastName();
    }

    @Override
    public String component12() {
        return getComments();
    }

    @Override
    public Integer component13() {
        return getLoginCount();
    }

    @Override
    public Integer component14() {
        return getLoginFailures();
    }

    @Override
    public Long component15() {
        return getLastLoginMs();
    }

    @Override
    public Long component16() {
        return getReactivatedMs();
    }

    @Override
    public Boolean component17() {
        return getForcePasswordChange();
    }

    @Override
    public Boolean component18() {
        return getNeverExpires();
    }

    @Override
    public Boolean component19() {
        return getEnabled();
    }

    @Override
    public Boolean component20() {
        return getInactive();
    }

    @Override
    public Boolean component21() {
        return getLocked();
    }

    @Override
    public Boolean component22() {
        return getProcessingAccount();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getVersion();
    }

    @Override
    public Long value3() {
        return getCreateTimeMs();
    }

    @Override
    public String value4() {
        return getCreateUser();
    }

    @Override
    public Long value5() {
        return getUpdateTimeMs();
    }

    @Override
    public String value6() {
        return getUpdateUser();
    }

    @Override
    public String value7() {
        return getEmail();
    }

    @Override
    public String value8() {
        return getPasswordHash();
    }

    @Override
    public Long value9() {
        return getPasswordLastChangedMs();
    }

    @Override
    public String value10() {
        return getFirstName();
    }

    @Override
    public String value11() {
        return getLastName();
    }

    @Override
    public String value12() {
        return getComments();
    }

    @Override
    public Integer value13() {
        return getLoginCount();
    }

    @Override
    public Integer value14() {
        return getLoginFailures();
    }

    @Override
    public Long value15() {
        return getLastLoginMs();
    }

    @Override
    public Long value16() {
        return getReactivatedMs();
    }

    @Override
    public Boolean value17() {
        return getForcePasswordChange();
    }

    @Override
    public Boolean value18() {
        return getNeverExpires();
    }

    @Override
    public Boolean value19() {
        return getEnabled();
    }

    @Override
    public Boolean value20() {
        return getInactive();
    }

    @Override
    public Boolean value21() {
        return getLocked();
    }

    @Override
    public Boolean value22() {
        return getProcessingAccount();
    }

    @Override
    public AccountRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public AccountRecord value2(Integer value) {
        setVersion(value);
        return this;
    }

    @Override
    public AccountRecord value3(Long value) {
        setCreateTimeMs(value);
        return this;
    }

    @Override
    public AccountRecord value4(String value) {
        setCreateUser(value);
        return this;
    }

    @Override
    public AccountRecord value5(Long value) {
        setUpdateTimeMs(value);
        return this;
    }

    @Override
    public AccountRecord value6(String value) {
        setUpdateUser(value);
        return this;
    }

    @Override
    public AccountRecord value7(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public AccountRecord value8(String value) {
        setPasswordHash(value);
        return this;
    }

    @Override
    public AccountRecord value9(Long value) {
        setPasswordLastChangedMs(value);
        return this;
    }

    @Override
    public AccountRecord value10(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public AccountRecord value11(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public AccountRecord value12(String value) {
        setComments(value);
        return this;
    }

    @Override
    public AccountRecord value13(Integer value) {
        setLoginCount(value);
        return this;
    }

    @Override
    public AccountRecord value14(Integer value) {
        setLoginFailures(value);
        return this;
    }

    @Override
    public AccountRecord value15(Long value) {
        setLastLoginMs(value);
        return this;
    }

    @Override
    public AccountRecord value16(Long value) {
        setReactivatedMs(value);
        return this;
    }

    @Override
    public AccountRecord value17(Boolean value) {
        setForcePasswordChange(value);
        return this;
    }

    @Override
    public AccountRecord value18(Boolean value) {
        setNeverExpires(value);
        return this;
    }

    @Override
    public AccountRecord value19(Boolean value) {
        setEnabled(value);
        return this;
    }

    @Override
    public AccountRecord value20(Boolean value) {
        setInactive(value);
        return this;
    }

    @Override
    public AccountRecord value21(Boolean value) {
        setLocked(value);
        return this;
    }

    @Override
    public AccountRecord value22(Boolean value) {
        setProcessingAccount(value);
        return this;
    }

    @Override
    public AccountRecord values(Integer value1, Integer value2, Long value3, String value4, Long value5, String value6, String value7, String value8, Long value9, String value10, String value11, String value12, Integer value13, Integer value14, Long value15, Long value16, Boolean value17, Boolean value18, Boolean value19, Boolean value20, Boolean value21, Boolean value22) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountRecord
     */
    public AccountRecord() {
        super(Account.ACCOUNT);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(Integer id, Integer version, Long createTimeMs, String createUser, Long updateTimeMs, String updateUser, String email, String passwordHash, Long passwordLastChangedMs, String firstName, String lastName, String comments, Integer loginCount, Integer loginFailures, Long lastLoginMs, Long reactivatedMs, Boolean forcePasswordChange, Boolean neverExpires, Boolean enabled, Boolean inactive, Boolean locked, Boolean processingAccount) {
        super(Account.ACCOUNT);

        set(0, id);
        set(1, version);
        set(2, createTimeMs);
        set(3, createUser);
        set(4, updateTimeMs);
        set(5, updateUser);
        set(6, email);
        set(7, passwordHash);
        set(8, passwordLastChangedMs);
        set(9, firstName);
        set(10, lastName);
        set(11, comments);
        set(12, loginCount);
        set(13, loginFailures);
        set(14, lastLoginMs);
        set(15, reactivatedMs);
        set(16, forcePasswordChange);
        set(17, neverExpires);
        set(18, enabled);
        set(19, inactive);
        set(20, locked);
        set(21, processingAccount);
    }
}
