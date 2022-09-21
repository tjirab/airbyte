/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers.temporal.discover.catalog;

import datadog.trace.api.Trace;
import io.airbyte.config.ConnectorJobOutput;
import io.airbyte.config.StandardDiscoverCatalogInput;
import io.airbyte.persistence.job.models.IntegrationLauncherConfig;
import io.airbyte.persistence.job.models.JobRunConfig;
import io.airbyte.workers.TraceUtils;
import io.airbyte.workers.temporal.annotations.TemporalActivityStub;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class DiscoverCatalogWorkflowImpl implements DiscoverCatalogWorkflow {

  @TemporalActivityStub(activityOptionsBeanName = "discoveryActivityOptions")
  private DiscoverCatalogActivity activity;

  @Trace(operationName = "workflow.discover.catalog")
  @Override
  public ConnectorJobOutput run(final JobRunConfig jobRunConfig,
                                final IntegrationLauncherConfig launcherConfig,
                                final StandardDiscoverCatalogInput config) {
    TraceUtils.addTagsToTrace(Map.of("job-id", jobRunConfig.getJobId(), "docker-image", launcherConfig.getDockerImage()));
    return activity.run(jobRunConfig, launcherConfig, config);
  }

}
