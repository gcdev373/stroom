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

package stroom.processor.impl.db;

import stroom.db.util.JooqUtil;
import stroom.processor.impl.db.jooq.tables.records.ProcessorFeedRecord;
import stroom.util.shared.Clearable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static stroom.processor.impl.db.jooq.tables.ProcessorFeed.PROCESSOR_FEED;

@Singleton
class ProcessorFeedCache implements Clearable {
    // TODO : @66 Replace with a proper cache.
    private final Map<String, Integer> cache = new ConcurrentHashMap<>();

    private final ProcessorDbConnProvider processorDbConnProvider;

    @Inject
    ProcessorFeedCache(final ProcessorDbConnProvider processorDbConnProvider) {
        this.processorDbConnProvider = processorDbConnProvider;
    }

    //    @Override
    public Integer getOrCreate(final String name) {
        // Try and get the id from the cache.
        return Optional.ofNullable(cache.get(name))
                .or(() -> {
                    // Try and get the existing id from the DB.
                    return get(name)
                            .or(() -> {
                                create(name);
                                return get(name);
                            })
                            .map(path -> {
                                // Cache for next time.
                                cache.put(name, path);
                                return path;
                            });
                }).orElseThrow();
    }

//    @Override
//    public List<String> list() {
//        return JooqUtil.contextResult(connectionProvider, context -> context
//                .select(PROCESSOR_NODE.NAME)
//                .from(PROCESSOR_NODE)
//                .fetch(PROCESSOR_NODE.NAME));
//    }

    private Optional<Integer> get(final String name) {
        return JooqUtil.contextResult(processorDbConnProvider, context -> context
                .select(PROCESSOR_FEED.ID)
                .from(PROCESSOR_FEED)
                .where(PROCESSOR_FEED.NAME.eq(name))
                .fetchOptional(PROCESSOR_FEED.ID));
    }

    private Optional<Integer> create(final String name) {
        return JooqUtil.contextResult(processorDbConnProvider, context -> context
                .insertInto(PROCESSOR_FEED, PROCESSOR_FEED.NAME)
                .values(name)
                .onDuplicateKeyIgnore()
                .returning(PROCESSOR_FEED.ID)
                .fetchOptional()
                .map(ProcessorFeedRecord::getId));
    }

    @Override
    public void clear() {
        deleteAll();
        cache.clear();
    }

    int deleteAll() {
        return JooqUtil.contextResult(processorDbConnProvider, context -> context
                .delete(PROCESSOR_FEED)
                .execute());
    }
}
