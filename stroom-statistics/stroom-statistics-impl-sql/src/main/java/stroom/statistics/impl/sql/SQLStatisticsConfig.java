package stroom.statistics.impl.sql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import stroom.config.common.DbConfig;
import stroom.config.common.HasDbConfig;
import stroom.statistics.impl.sql.search.SearchConfig;
import stroom.util.shared.IsConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SQLStatisticsConfig implements IsConfig, HasDbConfig {
    private DbConfig dbConfig;
    private String docRefType = "StatisticStore";
    private SearchConfig searchConfig;
    private int statisticAggregationBatchSize = 1000000;
    private String maxProcessingAge;

    public SQLStatisticsConfig() {
        searchConfig = new SearchConfig();
        this.dbConfig = new DbConfig();
    }

    @Inject
    public SQLStatisticsConfig(final SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
        this.dbConfig = new DbConfig();
    }

    @JsonProperty("db")
    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(final DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @JsonPropertyDescription("The entity type for the sql statistics service")
    public String getDocRefType() {
        return docRefType;
    }

    public void setDocRefType(final String docRefType) {
        this.docRefType = docRefType;
    }

    @JsonProperty("search")
    public SearchConfig getSearchConfig() {
        return searchConfig;
    }

    public void setSearchConfig(final SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    @JsonPropertyDescription("Number of SQL_STAT_VAL_SRC records to merge into SQL_STAT_VAL in one batch")
    public int getStatisticAggregationBatchSize() {
        return statisticAggregationBatchSize;
    }

    public void setStatisticAggregationBatchSize(final int statisticAggregationBatchSize) {
        this.statisticAggregationBatchSize = statisticAggregationBatchSize;
    }

    @JsonPropertyDescription("The maximum age (e.g. '90d') of statistics to process and retain, i.e. any statistics with an statistic event time older than the current time minus maxProcessingAge will be silently dropped.  Existing statistic data over this age will be purged during statistic aggregation. Leave blank to process/retain all data.")
    public String getMaxProcessingAge() {
        return maxProcessingAge;
    }

    public void setMaxProcessingAge(final String maxProcessingAge) {
        this.maxProcessingAge = maxProcessingAge;
    }

    @Override
    public String toString() {
        return "SQLStatisticsConfig{" +
                "dbConfig=" + dbConfig +
                ", docRefType='" + docRefType + '\'' +
                ", searchConfig=" + searchConfig +
                ", statisticAggregationBatchSize=" + statisticAggregationBatchSize +
                ", maxProcessingAge='" + maxProcessingAge + '\'' +
                '}';
    }
}