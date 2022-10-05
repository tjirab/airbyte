/*
 * Copyright (c) 2022 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.server.handlers;

import io.airbyte.api.model.generated.Geography;
import io.airbyte.api.model.generated.WebBackendGeographiesListResult;
import java.util.Arrays;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class WebBackendGeographiesHandler {

  public WebBackendGeographiesListResult listGeographiesOSS() {
    // for now, OSS only supports AUTO. This can evolve to account for complex OSS use cases
    return new WebBackendGeographiesListResult().geographies(
        Collections.singletonList(Geography.AUTO));
  }

  /**
   * Only called by the wrapped Cloud API to enable multi-cloud
   */
  public WebBackendGeographiesListResult listGeographiesCloud() {
    return new WebBackendGeographiesListResult().geographies(Arrays.asList(Geography.values()));
  }

}
