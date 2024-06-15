package com.example.recruitment.sentry;

import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class SentryException{

  public void capture(Exception e, HttpStatusCode status) {
    SentryEvent event = new SentryEvent(e);
    if (status.is5xxServerError()) {
      event.setLevel(SentryLevel.ERROR);
    } else {
      event.setLevel(SentryLevel.DEBUG);
    }
      Sentry.captureEvent(event);
  }
}
