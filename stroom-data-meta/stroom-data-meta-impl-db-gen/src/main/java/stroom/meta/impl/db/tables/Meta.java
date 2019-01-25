/*
 * This file is generated by jOOQ.
 */
package stroom.meta.impl.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.meta.impl.db.Indexes;
import stroom.meta.impl.db.Keys;
import stroom.meta.impl.db.Stroom;
import stroom.meta.impl.db.tables.records.MetaRecord;


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
public class Meta extends TableImpl<MetaRecord> {

    private static final long serialVersionUID = -1156380933;

    /**
     * The reference instance of <code>stroom.meta</code>
     */
    public static final Meta META = new Meta();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MetaRecord> getRecordType() {
        return MetaRecord.class;
    }

    /**
     * The column <code>stroom.meta.id</code>.
     */
    public final TableField<MetaRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.meta.version</code>.
     */
    public final TableField<MetaRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.meta.create_time</code>.
     */
    public final TableField<MetaRecord, Long> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.meta.effective_time</code>.
     */
    public final TableField<MetaRecord, Long> EFFECTIVE_TIME = createField("effective_time", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.meta.parent_id</code>.
     */
    public final TableField<MetaRecord, Long> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.meta.status</code>.
     */
    public final TableField<MetaRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

    /**
     * The column <code>stroom.meta.status_time</code>.
     */
    public final TableField<MetaRecord, Long> STATUS_TIME = createField("status_time", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.meta.task_id</code>.
     */
    public final TableField<MetaRecord, Long> TASK_ID = createField("task_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.meta.feed_id</code>.
     */
    public final TableField<MetaRecord, Integer> FEED_ID = createField("feed_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.meta.type_id</code>.
     */
    public final TableField<MetaRecord, Integer> TYPE_ID = createField("type_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.meta.processor_id</code>.
     */
    public final TableField<MetaRecord, Integer> PROCESSOR_ID = createField("processor_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>stroom.meta</code> table reference
     */
    public Meta() {
        this(DSL.name("meta"), null);
    }

    /**
     * Create an aliased <code>stroom.meta</code> table reference
     */
    public Meta(String alias) {
        this(DSL.name(alias), META);
    }

    /**
     * Create an aliased <code>stroom.meta</code> table reference
     */
    public Meta(Name alias) {
        this(alias, META);
    }

    private Meta(Name alias, Table<MetaRecord> aliased) {
        this(alias, aliased, null);
    }

    private Meta(Name alias, Table<MetaRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Meta(Table<O> child, ForeignKey<O, MetaRecord> key) {
        super(child, key, META);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Stroom.STROOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.META_META_FEED_ID, Indexes.META_META_PROCESSOR_ID, Indexes.META_META_TYPE_ID, Indexes.META_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MetaRecord, Long> getIdentity() {
        return Keys.IDENTITY_META;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MetaRecord> getPrimaryKey() {
        return Keys.KEY_META_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MetaRecord>> getKeys() {
        return Arrays.<UniqueKey<MetaRecord>>asList(Keys.KEY_META_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<MetaRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MetaRecord, ?>>asList(Keys.META_FEED_ID, Keys.META_TYPE_ID, Keys.META_PROCESSOR_ID);
    }

    public MetaFeed metaFeed() {
        return new MetaFeed(this, Keys.META_FEED_ID);
    }

    public MetaType metaType() {
        return new MetaType(this, Keys.META_TYPE_ID);
    }

    public MetaProcessor metaProcessor() {
        return new MetaProcessor(this, Keys.META_PROCESSOR_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableField<MetaRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meta as(String alias) {
        return new Meta(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meta as(Name alias) {
        return new Meta(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Meta rename(String name) {
        return new Meta(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Meta rename(Name name) {
        return new Meta(name, null);
    }
}