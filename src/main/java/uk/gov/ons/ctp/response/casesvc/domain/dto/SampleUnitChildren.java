package uk.gov.ons.ctp.response.casesvc.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class SampleUnitChildren {

  protected List<SampleUnit> sampleUnitchildren;

  public SampleUnitChildren() {}

  public SampleUnitChildren(List<SampleUnit> sampleUnitchildren) {
    this.sampleUnitchildren = sampleUnitchildren;
  }

  public List<SampleUnit> getSampleUnitchildren() {
    if (this.sampleUnitchildren == null) {
      this.sampleUnitchildren = new ArrayList();
    }

    return this.sampleUnitchildren;
  }
}
