package uk.gov.ons.ctp.response.casesvc.message.sampleunitnotification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleUnit {
  protected String id;
  protected String sampleUnitRef;
  protected String sampleUnitType;
  protected String partyId;
  protected String collectionInstrumentId;
  protected String actionPlanId;
}
