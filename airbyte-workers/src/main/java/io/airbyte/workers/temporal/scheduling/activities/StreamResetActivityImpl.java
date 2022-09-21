/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers.temporal.scheduling.activities;

import datadog.trace.api.Trace;
import io.airbyte.workers.TraceUtils;
import io.airbyte.workers.temporal.StreamResetRecordsHelper;
import io.micronaut.context.annotation.Requires;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Singleton
@Requires(property = "airbyte.worker.plane",
          pattern = "(?i)^(?!data_plane).*")
public class StreamResetActivityImpl implements StreamResetActivity {

  @Inject
  private StreamResetRecordsHelper streamResetRecordsHelper;

  @Trace(operationName="activity")
  @Override
  public void deleteStreamResetRecordsForJob(final DeleteStreamResetRecordsForJobInput input) {
    TraceUtils.addTagsToTrace(Map.of("connection-id", input.getConnectionId(), "job-id", input.getJobId()));
    streamResetRecordsHelper.deleteStreamResetRecordsForJob(input.getJobId(), input.getConnectionId());
  }

}
