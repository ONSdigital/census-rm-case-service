package uk.gov.ons.ctp.response.casesvc.domain.dto;

public class SampleUnitParent extends SampleUnit {
  protected String collectionExerciseId;
  protected SampleUnitChildren sampleUnitChildren;

  public SampleUnitParent() {}

  public SampleUnitParent(
      String id,
      String sampleUnitRef,
      String sampleUnitType,
      String partyId,
      String collectionInstrumentId,
      String actionPlanId,
      String collectionExerciseId,
      SampleUnitChildren sampleUnitChildren) {
    super(id, sampleUnitRef, sampleUnitType, partyId, collectionInstrumentId, actionPlanId);
    this.collectionExerciseId = collectionExerciseId;
    this.sampleUnitChildren = sampleUnitChildren;
  }

  public String getCollectionExerciseId() {
    return this.collectionExerciseId;
  }

  public void setCollectionExerciseId(String value) {
    this.collectionExerciseId = value;
  }

  public SampleUnitChildren getSampleUnitChildren() {
    return this.sampleUnitChildren;
  }

  public void setSampleUnitChildren(SampleUnitChildren value) {
    this.sampleUnitChildren = value;
  }
}
