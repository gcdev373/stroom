/*
 * This file is generated by jOOQ.
 */
package stroom.processor.impl.db.jooq.tables;


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

import stroom.processor.impl.db.jooq.Indexes;
import stroom.processor.impl.db.jooq.Keys;
import stroom.processor.impl.db.jooq.Stroom;
import stroom.processor.impl.db.jooq.tables.records.ProcessorTaskRecord;


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
public class ProcessorTask extends TableImpl<ProcessorTaskRecord> {

    private static final long serialVersionUID = 402767732;

    /**
     * The reference instance of <code>stroom.processor_task</code>
     */
    public static final ProcessorTask PROCESSOR_TASK = new ProcessorTask();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProcessorTaskRecord> getRecordType() {
        return ProcessorTaskRecord.class;
    }

    /**
     * The column <code>stroom.processor_task.id</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.processor_task.version</code>.
     */
    public final TableField<ProcessorTaskRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.processor_task.fk_processor_filter_id</code>.
     */
    public final TableField<ProcessorTaskRecord, Integer> FK_PROCESSOR_FILTER_ID = createField("fk_processor_filter_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.processor_task.fk_processor_node_id</code>.
     */
    public final TableField<ProcessorTaskRecord, Integer> FK_PROCESSOR_NODE_ID = createField("fk_processor_node_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>stroom.processor_task.fk_processor_feed_id</code>.
     */
    public final TableField<ProcessorTaskRecord, Integer> FK_PROCESSOR_FEED_ID = createField("fk_processor_feed_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>stroom.processor_task.create_time_ms</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> CREATE_TIME_MS = createField("create_time_ms", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.processor_task.start_time_ms</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> START_TIME_MS = createField("start_time_ms", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.processor_task.end_time_ms</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> END_TIME_MS = createField("end_time_ms", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.processor_task.status</code>.
     */
    public final TableField<ProcessorTaskRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

    /**
     * The column <code>stroom.processor_task.status_time_ms</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> STATUS_TIME_MS = createField("status_time_ms", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.processor_task.meta_id</code>.
     */
    public final TableField<ProcessorTaskRecord, Long> META_ID = createField("meta_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.processor_task.data</code>.
     */
    public final TableField<ProcessorTaskRecord, String> DATA = createField("data", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>stroom.processor_task</code> table reference
     */
    public ProcessorTask() {
        this(DSL.name("processor_task"), null);
    }

    /**
     * Create an aliased <code>stroom.processor_task</code> table reference
     */
    public ProcessorTask(String alias) {
        this(DSL.name(alias), PROCESSOR_TASK);
    }

    /**
     * Create an aliased <code>stroom.processor_task</code> table reference
     */
    public ProcessorTask(Name alias) {
        this(alias, PROCESSOR_TASK);
    }

    private ProcessorTask(Name alias, Table<ProcessorTaskRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProcessorTask(Name alias, Table<ProcessorTaskRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ProcessorTask(Table<O> child, ForeignKey<O, ProcessorTaskRecord> key) {
        super(child, key, PROCESSOR_TASK);
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
        return Arrays.<Index>asList(Indexes.PROCESSOR_TASK_PRIMARY, Indexes.PROCESSOR_TASK_PROCESSOR_TASK_FK_PROCESSOR_FEED_ID, Indexes.PROCESSOR_TASK_PROCESSOR_TASK_FK_PROCESSOR_FILTER_ID, Indexes.PROCESSOR_TASK_PROCESSOR_TASK_FK_PROCESSOR_NODE_ID, Indexes.PROCESSOR_TASK_PROCESSOR_TASK_META_ID_IDX, Indexes.PROCESSOR_TASK_PROCESSOR_TASK_STATUS_IDX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProcessorTaskRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROCESSOR_TASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProcessorTaskRecord> getPrimaryKey() {
        return Keys.KEY_PROCESSOR_TASK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProcessorTaskRecord>> getKeys() {
        return Arrays.<UniqueKey<ProcessorTaskRecord>>asList(Keys.KEY_PROCESSOR_TASK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProcessorTaskRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProcessorTaskRecord, ?>>asList(Keys.PROCESSOR_TASK_FK_PROCESSOR_FILTER_ID, Keys.PROCESSOR_TASK_FK_PROCESSOR_NODE_ID, Keys.PROCESSOR_TASK_FK_PROCESSOR_FEED_ID);
    }

    public ProcessorFilter processorFilter() {
        return new ProcessorFilter(this, Keys.PROCESSOR_TASK_FK_PROCESSOR_FILTER_ID);
    }

    public ProcessorNode processorNode() {
        return new ProcessorNode(this, Keys.PROCESSOR_TASK_FK_PROCESSOR_NODE_ID);
    }

    public ProcessorFeed processorFeed() {
        return new ProcessorFeed(this, Keys.PROCESSOR_TASK_FK_PROCESSOR_FEED_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableField<ProcessorTaskRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorTask as(String alias) {
        return new ProcessorTask(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorTask as(Name alias) {
        return new ProcessorTask(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProcessorTask rename(String name) {
        return new ProcessorTask(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProcessorTask rename(Name name) {
        return new ProcessorTask(name, null);
    }
}
