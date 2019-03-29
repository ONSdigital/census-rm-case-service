package uk.gov.ons.ctp.response.casesvc.domain.dto;

public class SampleUnit {
  protected String id;
  protected String sampleUnitRef;
  protected String sampleUnitType;
  protected String partyId;
  protected String collectionInstrumentId;
  protected String actionPlanId;

  public SampleUnit() {}

  public SampleUnit(
      String id,
      String sampleUnitRef,
      String sampleUnitType,
      String partyId,
      String collectionInstrumentId,
      String actionPlanId) {
    this.id = id;
    this.sampleUnitRef = sampleUnitRef;
    this.sampleUnitType = sampleUnitType;
    this.partyId = partyId;
    this.collectionInstrumentId = collectionInstrumentId;
    this.actionPlanId = actionPlanId;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String value) {
    this.id = value;
  }

  public String getSampleUnitRef() {
    return this.sampleUnitRef;
  }

  public void setSampleUnitRef(String value) {
    this.sampleUnitRef = value;
  }

  public String getSampleUnitType() {
    return this.sampleUnitType;
  }

  public void setSampleUnitType(String value) {
    this.sampleUnitType = value;
  }

  public String getPartyId() {
    return this.partyId;
  }

  public void setPartyId(String value) {
    this.partyId = value;
  }

  public String getCollectionInstrumentId() {
    return this.collectionInstrumentId;
  }

  public void setCollectionInstrumentId(String value) {
    this.collectionInstrumentId = value;
  }

  public String getActionPlanId() {
    return this.actionPlanId;
  }

  public void setActionPlanId(String value) {
    this.actionPlanId = value;
  }
}
