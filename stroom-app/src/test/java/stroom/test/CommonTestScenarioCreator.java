/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package stroom.test;


import stroom.meta.shared.Meta;
import stroom.meta.shared.MetaProperties;
import stroom.meta.shared.MetaDataSource;
import stroom.data.store.api.StreamStore;
import stroom.data.store.api.StreamTarget;
import stroom.data.store.api.StreamTargetUtil;
import stroom.docref.DocRef;
import stroom.meta.shared.StroomHeaderArguments;
import stroom.index.IndexStore;
import stroom.index.IndexVolumeService;
import stroom.index.shared.IndexDoc;
import stroom.index.shared.IndexField;
import stroom.index.shared.IndexFields;
import stroom.node.api.NodeInfo;
import stroom.volume.VolumeService;
import stroom.node.shared.FindVolumeCriteria;
import stroom.node.shared.VolumeEntity;
import stroom.node.shared.VolumeEntity.VolumeUseStatus;
import stroom.query.api.v2.ExpressionOperator;
import stroom.query.api.v2.ExpressionTerm;
import stroom.streamstore.shared.QueryData;
import stroom.streamstore.shared.StreamTypeNames;
import stroom.streamtask.StreamProcessorFilterService;
import stroom.streamtask.StreamProcessorService;
import stroom.streamtask.shared.Processor;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Help class to create some basic scenarios for testing.
 */
public class CommonTestScenarioCreator {
    private final StreamStore streamStore;
    private final StreamProcessorService streamProcessorService;
    private final StreamProcessorFilterService streamProcessorFilterService;
    private final IndexStore indexStore;
    private final VolumeService volumeService;
    private final IndexVolumeService indexVolumeService;
    private final NodeInfo nodeInfo;

    @Inject
    CommonTestScenarioCreator(final StreamStore streamStore,
                              final StreamProcessorService streamProcessorService,
                              final StreamProcessorFilterService streamProcessorFilterService,
                              final IndexStore indexStore,
                              final VolumeService volumeService,
                              final IndexVolumeService indexVolumeService,
                              final NodeInfo nodeInfo) {
        this.streamStore = streamStore;
        this.streamProcessorService = streamProcessorService;
        this.streamProcessorFilterService = streamProcessorFilterService;
        this.indexStore = indexStore;
        this.volumeService = volumeService;
        this.indexVolumeService = indexVolumeService;
        this.nodeInfo = nodeInfo;
    }

    public void createBasicTranslateStreamProcessor(final String feed) {
        final QueryData findStreamQueryData = new QueryData.Builder()
                .dataSource(MetaDataSource.STREAM_STORE_DOC_REF)
                .expression(new ExpressionOperator.Builder(ExpressionOperator.Op.AND)
                        .addTerm(MetaDataSource.FEED_NAME, ExpressionTerm.Condition.EQUALS, feed)
                        .addTerm(MetaDataSource.STREAM_TYPE_NAME, ExpressionTerm.Condition.EQUALS, StreamTypeNames.RAW_EVENTS)
                        .build())
                .build();

        createStreamProcessor(findStreamQueryData);
    }

    public void createStreamProcessor(final QueryData queryData) {
        Processor streamProcessor = new Processor();
        streamProcessor.setEnabled(true);
        streamProcessor = streamProcessorService.save(streamProcessor);

        streamProcessorFilterService.addFindStreamCriteria(streamProcessor, 1, queryData);
    }

    public DocRef createIndex(final String name) {
        return createIndex(name, createIndexFields(), IndexDoc.DEFAULT_MAX_DOCS_PER_SHARD);
    }

    public DocRef createIndex(final String name, final List<IndexField> indexFields) {
        return createIndex(name, indexFields, IndexDoc.DEFAULT_MAX_DOCS_PER_SHARD);
    }

    public DocRef createIndex(final String name, final List<IndexField> indexFields, final int maxDocsPerShard) {
        // Create a test index.
        final DocRef indexRef = indexStore.createDocument(name);
        final IndexDoc index = indexStore.readDocument(indexRef);
        index.setMaxDocsPerShard(maxDocsPerShard);
        index.setIndexFields(indexFields);
        indexStore.writeDocument(index);
        assertThat(index).isNotNull();

        final FindVolumeCriteria findVolumeCriteria = new FindVolumeCriteria();
        findVolumeCriteria.getIndexStatusSet().add(VolumeUseStatus.ACTIVE);
        findVolumeCriteria.getNodeIdSet().add(nodeInfo.getThisNode());
        final Set<VolumeEntity> volumes = new HashSet<>(volumeService.find(findVolumeCriteria));
        indexVolumeService.setVolumesForIndex(indexRef, volumes);

        return indexRef;
    }

    public List<IndexField> createIndexFields() {
        final List<IndexField> indexFields = IndexFields.createStreamIndexFields();
        indexFields.add(IndexField.createField("test"));
        return indexFields;
    }

    /**
     * @param feed related
     * @return a basic raw file
     */
    public Meta createSample2LineRawFile(final String feed, final String streamType) {
        final MetaProperties streamProperties = new MetaProperties.Builder()
                .feedName(feed)
                .typeName(streamType)
                .build();
        final StreamTarget target = streamStore.openStreamTarget(streamProperties);
        StreamTargetUtil.write(target, "line1\nline2");
        target.getAttributes().put(StroomHeaderArguments.FEED, feed);

        streamStore.closeStreamTarget(target);
        return target.getStream();
    }

    public Meta createSampleBlankProcessedFile(final String feed, final Meta sourceStream) {
        final MetaProperties streamProperties = new MetaProperties.Builder()
                .feedName(feed)
                .typeName(StreamTypeNames.EVENTS)
                .parent(sourceStream)
                .build();

        final StreamTarget target = streamStore.openStreamTarget(streamProperties);
        final String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<Events xpath-default-namespace=\"records:2\" "
                + "xmlns:stroom=\"stroom\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns=\"event-logging:3\" "
                + "xsi:schemaLocation=\"event-logging:3 file://event-logging-v3.0.0.xsd\" "
                + "Version=\"3.0.0\"/>";
        StreamTargetUtil.write(target, data);
        streamStore.closeStreamTarget(target);
        return target.getStream();
    }
}