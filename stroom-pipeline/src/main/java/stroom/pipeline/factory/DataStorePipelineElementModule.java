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
 */

package stroom.pipeline.factory;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import stroom.entity.shared.Clearable;
import stroom.pipeline.filter.XsltFilter;

public class DataStorePipelineElementModule extends AbstractModule {
    @Override
    protected void configure() {
        final Multibinder<Element> elementBinder = Multibinder.newSetBinder(binder(), Element.class);
        elementBinder.addBinding().to(stroom.pipeline.writer.HDFSFileAppender.class);
        elementBinder.addBinding().to(stroom.pipeline.writer.RollingStreamAppender.class);
        elementBinder.addBinding().to(stroom.pipeline.writer.StreamAppender.class);
    }
}