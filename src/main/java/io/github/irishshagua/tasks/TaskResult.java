package io.github.irishshagua.tasks;

import java.time.Duration;

public record TaskResult(Boolean successful, String result, Duration executionTime) {}
