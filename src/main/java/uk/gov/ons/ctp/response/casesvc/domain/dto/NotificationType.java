package uk.gov.ons.ctp.response.casesvc.domain.dto;

public enum NotificationType {
  ACTIVATED,
  REPLACED,
  DEACTIVATED,
  DISABLED,
  ACTIONPLAN_CHANGED;

  private NotificationType() {}

  public String value() {
    return this.name();
  }

  public static NotificationType fromValue(String v) {
    return valueOf(v);
  }
}
