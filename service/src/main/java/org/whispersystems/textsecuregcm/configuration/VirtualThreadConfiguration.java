/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.whispersystems.textsecuregcm.configuration;

import java.time.Duration;

public record VirtualThreadConfiguration(Duration pinEventThreshold) {}
