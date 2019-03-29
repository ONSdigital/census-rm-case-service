package uk.gov.ons.ctp.response.casesvc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseNotification {
  private String sampleUnitId;
  private String caseId;
  private String actionPlanId;
  private String exerciseId;
  private String partyId;
  private String sampleUnitType;
  private NotificationType notificationType;
}
