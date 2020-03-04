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

package stroom.node.mock;

import stroom.node.api.NodeService;
import stroom.node.api.FindNodeCriteria;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

/**
 * Mock class that manages one node.
 */
@Singleton
public class MockNodeService implements NodeService {
    private MockNodeInfo nodeInfo = new MockNodeInfo();

    @Override
    public String getClusterUrl(final String nodeName) {
        return null;
    }

    @Override
    public boolean isEnabled(final String nodeName) {
        return nodeName.equals(nodeInfo.getThisNodeName());
    }

    @Override
    public int getPriority(final String nodeName) {
        if (nodeName.equals(nodeInfo.getThisNodeName())) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<String> findNodeNames(final FindNodeCriteria criteria) {
        return Collections.singletonList(nodeInfo.getThisNodeName());
    }
}
