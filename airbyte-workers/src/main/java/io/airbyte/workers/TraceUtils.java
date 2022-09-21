package io.airbyte.workers;

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

import java.util.Map;

/**
 * Collection of utility methods to help with performance tracing.
 */
public class TraceUtils {

  /**
   * Adds all the provided tags to the currently active span, if one exists.
   * @param tags A map of tags to be added to the currently active span.
   */
  public static void addTagsToTrace(final Map<String, Object> tags) {
    final Span span = GlobalTracer.get().activeSpan();
    if (span != null) {
      tags.entrySet().forEach(entry -> {
        span.setTag(entry.getKey(), entry.getValue().toString());
      });
    }
  }
}
