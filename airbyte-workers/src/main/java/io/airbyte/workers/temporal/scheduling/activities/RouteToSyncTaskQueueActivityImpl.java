/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers.temporal.scheduling.activities;

import datadog.trace.api.Trace;
import io.airbyte.workers.TraceUtils;
import io.airbyte.workers.temporal.sync.RouterService;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RouteToSyncTaskQueueActivityImpl implements RouteToSyncTaskQueueActivity {

  @Inject
  private RouterService routerService;

  @Trace(operationName="activity")
  @Override
  public RouteToSyncTaskQueueOutput route(final RouteToSyncTaskQueueInput input) {
    TraceUtils.addTagsToTrace(Map.of("connection-id", input.getConnectionId()));
    final String taskQueueForConnectionId = routerService.getTaskQueue(input.getConnectionId());

    return new RouteToSyncTaskQueueOutput(taskQueueForConnectionId);
  }

}
