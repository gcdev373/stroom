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

package stroom.node.impl;


import org.junit.jupiter.api.Test;
import stroom.node.shared.FindVolumeCriteria;
import stroom.node.shared.VolumeEntity;
import stroom.test.AbstractCoreIntegrationTest;
import stroom.volume.VolumeService;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestVolumeService extends AbstractCoreIntegrationTest {
    @Inject
    private VolumeService volumeService;

    @Test
    void testFind() {
        final FindVolumeCriteria criteria = new FindVolumeCriteria();
        final List<VolumeEntity> volumeList = volumeService.find(criteria);

        for (final VolumeEntity volume : volumeList) {
            volumeService.save(volume);
        }

        assertThat(volumeService.find(criteria)).hasSize(volumeList.size());
    }
}