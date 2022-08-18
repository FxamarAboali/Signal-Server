/*
 * Copyright 2013-2020 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.whispersystems.textsecuregcm.sms;


import java.util.List;
import java.util.Locale.LanguageRange;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SmsSender {

  private final TwilioSmsSender twilioSender;

  public SmsSender(TwilioSmsSender twilioSender) {
    this.twilioSender = twilioSender;
  }

  public void deliverSmsVerification(String destination, Optional<String> clientType, String verificationCode) {
    destination = fixMexicoNumbers(destination);

    twilioSender.deliverSmsVerification(destination, clientType, verificationCode);
  }

  public void deliverVoxVerification(String destination, String verificationCode, List<LanguageRange> languageRanges) {
    twilioSender.deliverVoxVerification(destination, verificationCode, languageRanges);
  }

  public CompletableFuture<Optional<String>> deliverSmsVerificationWithTwilioVerify(String destination,
      Optional<String> clientType,
      String verificationCode, List<LanguageRange> languageRanges) {

    destination = fixMexicoNumbers(destination);

    return twilioSender.deliverSmsVerificationWithVerify(destination, clientType, verificationCode, languageRanges);
  }

  public CompletableFuture<Optional<String>> deliverVoxVerificationWithTwilioVerify(String destination,
      String verificationCode,
      List<LanguageRange> languageRanges) {

    return twilioSender.deliverVoxVerificationWithVerify(destination, verificationCode, languageRanges);
  }

  public void reportVerificationSucceeded(String verificationSid, @Nullable String userAgent, String context) {
    twilioSender.reportVerificationSucceeded(verificationSid, userAgent, context);
  }

  private String fixMexicoNumbers(String destination) {
    // Fix up mexico numbers to 'mobile' format just for SMS delivery.
    if (destination.startsWith("+52") && !destination.startsWith("+521")) {
      destination = "+521" + destination.substring("+52".length());
    }
    return destination;
  }
}
