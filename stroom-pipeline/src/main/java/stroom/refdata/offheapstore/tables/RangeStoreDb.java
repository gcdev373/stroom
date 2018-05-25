/*
 * Copyright 2018 Crown Copyright
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

package stroom.refdata.offheapstore.tables;

import com.google.inject.assistedinject.Assisted;
import org.lmdbjava.Env;
import stroom.refdata.lmdb.AbstractLmdbDb;
import stroom.refdata.offheapstore.RangeStoreKey;
import stroom.refdata.offheapstore.serdes.RangeStoreKeySerde;
import stroom.refdata.offheapstore.ValueStoreKey;
import stroom.refdata.offheapstore.serdes.ValueStoreKeySerde;

import javax.inject.Inject;
import java.nio.ByteBuffer;

public class RangeStoreDb extends AbstractLmdbDb<RangeStoreKey, ValueStoreKey> {

    private static final String DB_NAME = "RangeStore";

    @Inject
    public RangeStoreDb(@Assisted final Env<ByteBuffer> lmdbEnvironment,
                        final RangeStoreKeySerde keySerde,
                        final ValueStoreKeySerde valueSerde) {

        super(lmdbEnvironment, keySerde, valueSerde, DB_NAME);
    }

    public interface Factory {
        RangeStoreDb create(final Env<ByteBuffer> lmdbEnvironment);
    }
}
