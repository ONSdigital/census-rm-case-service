package uk.gov.ons.ctp.response.casesvc.message.notification;

import uk.gov.ons.ctp.response.casesvc.domain.dto.CaseNotification;

public class ObjectFactory {

  public ObjectFactory() {}

  /** Create an instance of {@link CaseNotification } */
  public CaseNotification createCaseNotification() {
    return new CaseNotification();
  }
}
