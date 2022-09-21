package io.airbyte.workers;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

import java.util.Map;

/**
 * Collection of utility methods to help with performance tracing.
 */
public class TraceUtils {

  private static final String TAG_PREFIX = "metadata";

  /**
   * Adds all the provided tags to the currently active span, if one exists.
   * <br />
   * All tags added via this method will use the default {@link #TAG_PREFIX} namespace.
   *
   * @param tags A map of tags to be added to the currently active span.
   */
  public static void addTagsToTrace(final Map<String, Object> tags) {
    addTagsToTrace(tags, TAG_PREFIX);
  }

  /**
   * Adds all provided tags to the currently active span, if one exists, under the
   * provided tag name namespace.
   * @param tags A map of tags to be added to the currently active span.
   * @param tagPrefix The prefix to be added to each custom tag name.
   */
  public static void addTagsToTrace(final Map<String, Object> tags, final String tagPrefix) {
    addTagsToTrace(GlobalTracer.get().activeSpan(), tags, tagPrefix);
  }

  /**
   * Adds all the provided tags to the currently active span, if one exists.
   * @param span The {@link Span} that will be associated with the tags.
   * @param tags A map of tags to be added to the currently active span.
   * @param tagPrefix The prefix to be added to each custom tag name.
   */
  public static void addTagsToTrace(final Span span, final Map<String, Object> tags, final String tagPrefix) {
    if (span != null) {
      tags.entrySet().forEach(entry -> {
        span.setTag(String.format("%s.%s", tagPrefix, entry.getKey()), entry.getValue().toString());
      });
    }
  }

  /**
   * Starts a child span for the current active trace.
   * @param operationName The operation name to be associated with the child span.
   * @return The {@link Scope} used to manage the child span.
   */
  public static Scope startChildSpan(final String operationName) {
    return GlobalTracer.get().buildSpan(operationName).startActive(true);
  }
}
