/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.workers.temporal.scheduling.activities;

import datadog.trace.api.Trace;
import io.airbyte.config.persistence.ConfigNotFoundException;
import io.airbyte.validation.json.JsonValidationException;
import io.airbyte.workers.TraceUtils;
import io.airbyte.workers.helper.ConnectionHelper;
import io.airbyte.workers.temporal.exception.RetryableException;
import io.micronaut.context.annotation.Requires;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Singleton
@Requires(property = "airbyte.worker.plane",
          pattern = "(?i)^(?!data_plane).*")
public class ConnectionDeletionActivityImpl implements ConnectionDeletionActivity {

  @Inject
  private ConnectionHelper connectionHelper;

  @Trace(operationName="activity")
  @Override
  public void deleteConnection(final ConnectionDeletionInput input) {
    try {
      TraceUtils.addTagsToTrace(Map.of("connection-id", input.getConnectionId()));
      connectionHelper.deleteConnection(input.getConnectionId());
    } catch (final JsonValidationException | ConfigNotFoundException | IOException e) {
      throw new RetryableException(e);
    }
  }

}
