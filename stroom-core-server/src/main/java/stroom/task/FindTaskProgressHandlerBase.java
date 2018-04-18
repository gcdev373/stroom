/*
 * Copyright 2016 Crown Copyright
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

package stroom.task;

import stroom.entity.cluster.FindServiceClusterTask;
import stroom.entity.shared.Action;
import stroom.entity.shared.BaseResultList;
import stroom.entity.shared.PageRequest;
import stroom.entity.shared.ResultList;
import stroom.entity.shared.Sort;
import stroom.node.shared.Node;
import stroom.task.cluster.ClusterCallEntry;
import stroom.task.cluster.ClusterDispatchAsyncHelper;
import stroom.task.cluster.DefaultClusterResultCollector;
import stroom.task.cluster.TargetNodeSetFactory.TargetType;
import stroom.task.shared.FindTaskProgressCriteria;
import stroom.task.shared.TaskProgress;
import stroom.util.shared.CompareUtil;
import stroom.util.shared.Expander;
import stroom.util.shared.SharedObject;
import stroom.util.shared.Task;
import stroom.util.shared.TaskId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

abstract class FindTaskProgressHandlerBase<T extends Task<R>, R extends SharedObject>
        extends AbstractTaskHandler<T, R> {
    private final ClusterDispatchAsyncHelper dispatchHelper;

    @Inject
    FindTaskProgressHandlerBase(final ClusterDispatchAsyncHelper dispatchHelper) {
        this.dispatchHelper = dispatchHelper;
    }

    BaseResultList<TaskProgress> doExec(final Action<?> action, final FindTaskProgressCriteria criteria) {
        final PageRequest originalPageRequest = criteria.getPageRequest();
        try {
            // Don't page limit the first query
            criteria.setPageRequest(new PageRequest());

            final FindServiceClusterTask<FindTaskProgressCriteria, TaskProgress> clusterTask = new FindServiceClusterTask<>(
                    action.getUserToken(), action.getTaskName(), TaskManager.class, criteria);
            final DefaultClusterResultCollector<ResultList<TaskProgress>> collector = dispatchHelper
                    .execAsync(clusterTask, TargetType.ACTIVE);

            final List<TaskProgress> totalList = new ArrayList<>();
            final Map<TaskId, TaskProgress> totalMap = new HashMap<>();
            final Map<TaskId, List<TaskProgress>> totalChildMap = new HashMap<>();

            for (final Entry<Node, ClusterCallEntry<ResultList<TaskProgress>>> entry : collector.getResponseMap()
                    .entrySet()) {
                if (entry.getValue().getResult() != null) {
                    for (final TaskProgress taskProgress : entry.getValue().getResult()) {
                        totalList.add(taskProgress);
                        totalMap.put(taskProgress.getId(), taskProgress);
                    }
                }
            }

            final List<TaskProgress> sortedRootNodes = new ArrayList<>();
            final List<TaskProgress> additionList = new ArrayList<>();
            for (final TaskProgress taskProgress : totalList) {
                final TaskId taskId = taskProgress.getId();
                TaskProgress child = taskProgress;
                boolean newChild = true;

                // Has it got ancestors?
                if (taskId != null && taskId.getParentId() != null) {
                    TaskId parentId = taskId.getParentId();

                    // Build relationships to parents creating dummy dead
                    // parents if necessary.
                    while (parentId != null) {
                        // See if we already know about this parent?
                        TaskProgress parent = totalMap.get(parentId);
                        final boolean unknownParent = parent == null;

                        if (unknownParent) {
                            // If we have no record of this parent then create a
                            // dummy dead one.
                            parent = new TaskProgress();
                            parent.setId(parentId);
                            parent.setSubmitTimeMs(child.getSubmitTimeMs());
                            parent.setTimeNowMs(child.getTimeNowMs());
                            parent.setTaskName("<<dead>>");

                            totalMap.put(parentId, parent);
                            additionList.add(parent);

                            // If the newly created node is a root then add it
                            // to the root list.
                            if (parentId.getParentId() == null) {
                                sortedRootNodes.add(parent);
                            }
                        }

                        // Only add the child to the child map for this parent
                        // if it is one that hasn't been added before.
                        if (newChild) {
                            // Add this task progress to the child list for this
                            // parent.
                            totalChildMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(child);
                        }

                        // Assign the parent to the child for the next ancestor
                        // child list to use.
                        child = parent;

                        // If we did not previously know anything about the
                        // parent and have had to create a dummy one then make
                        // sure the child map for the next parent contains the
                        // newly created dummy.
                        newChild = unknownParent;

                        parentId = parentId.getParentId();
                    }

                } else {
                    sortedRootNodes.add(taskProgress);
                }
            }
            totalList.addAll(additionList);

            final List<TaskProgress> rtnList = new ArrayList<>();

            sortTaskProgressList(sortedRootNodes, criteria);
            for (final TaskProgress taskProgress : sortedRootNodes) {
                buildTreeNode(rtnList, criteria, taskProgress, totalChildMap, 0);
            }

            return BaseResultList.createCriterialBasedList(rtnList, criteria);

        } finally {
            criteria.setPageRequest(originalPageRequest);
        }
    }

    private void sortTaskProgressList(final List<TaskProgress> rtnList, FindTaskProgressCriteria criteria) {
        rtnList.sort((TaskProgress lhs, TaskProgress rhs) -> {
            criteria.validateSortField();

            // We're only implementing sorting by a single column
            Sort sort = criteria.getSortList().isEmpty() ? new Sort() : criteria.getSortList().get(0);

            // We're not going to have a default for the direction because why would it be anything other than asc/desc?
            switch (sort.getField()) {
                case FindTaskProgressCriteria.FIELD_AGE:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareLong(lhs.getAgeMs(), rhs.getAgeMs());
                        case DESCENDING:
                            return CompareUtil.compareLong(rhs.getAgeMs(), lhs.getAgeMs());
                    }
                case FindTaskProgressCriteria.FIELD_INFO:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareString(lhs.getTaskInfo(), rhs.getTaskInfo());
                        case DESCENDING:
                            return CompareUtil.compareString(rhs.getTaskInfo(), lhs.getTaskInfo());
                    }
                case FindTaskProgressCriteria.FIELD_NAME:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareString(lhs.getTaskName(), rhs.getTaskName());
                        case DESCENDING:
                            return CompareUtil.compareString(rhs.getTaskName(), lhs.getTaskName());
                    }
                case FindTaskProgressCriteria.FIELD_NODE:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareString(lhs.getNode().getName(), rhs.getNode().getName());
                        case DESCENDING:
                            return CompareUtil.compareString(rhs.getNode().getName(), lhs.getNode().getName());
                    }
                case FindTaskProgressCriteria.FIELD_SUBMIT_TIME:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareLong(lhs.getSubmitTimeMs(), rhs.getSubmitTimeMs());
                        case DESCENDING:
                            return CompareUtil.compareLong(rhs.getSubmitTimeMs(), lhs.getSubmitTimeMs());
                    }
                case FindTaskProgressCriteria.FIELD_USER:
                    switch (sort.getDirection()) {
                        case ASCENDING:
                            return CompareUtil.compareString(lhs.getUserName(), rhs.getUserName());
                        case DESCENDING:
                            return CompareUtil.compareString(rhs.getUserName(), lhs.getUserName());
                    }
                default:
                    // If the field is valid, and it should be because we've validated it, and the sort direction is valid,
                    // and it should be because it's an enum and the only sensible sort directions are asc and desc,
                    // then we'll probably never get here.
                    return CompareUtil.compareLong(lhs.getSubmitTimeMs(), rhs.getSubmitTimeMs());
            }
        });
    }

    private void buildTreeNode(final List<TaskProgress> rtnList, final FindTaskProgressCriteria criteria,
                               final TaskProgress node, final Map<TaskId, List<TaskProgress>> totalChildMap, final int depth) {
        rtnList.add(node);
        final List<TaskProgress> childList = totalChildMap.get(node.getId());
        if (childList != null) {
            sortTaskProgressList(childList, criteria);
            // Force expansion on tasks that are younger than a second.
            final boolean forceExpansion = node.getAgeMs() < 1000;
            final boolean expanded = criteria.isExpanded(node);

            final boolean state = expanded || forceExpansion;
            node.setExpander(new Expander(depth, state, false));
            if (state) {
                for (final TaskProgress child : childList) {
                    buildTreeNode(rtnList, criteria, child, totalChildMap, depth + 1);
                }
            }
        } else {
            node.setExpander(new Expander(depth, false, true));
        }
    }
}
