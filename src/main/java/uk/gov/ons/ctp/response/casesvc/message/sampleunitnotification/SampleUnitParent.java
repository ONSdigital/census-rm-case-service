//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.11
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2019.03.29 at 02:02:23 PM GMT
//

package uk.gov.ons.ctp.response.casesvc.message.sampleunitnotification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleUnitParent extends SampleUnit {
  protected String collectionExerciseId;
  protected SampleUnitChildren sampleUnitChildren;
}