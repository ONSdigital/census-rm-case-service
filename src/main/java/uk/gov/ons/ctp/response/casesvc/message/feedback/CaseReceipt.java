package uk.gov.ons.ctp.response.casesvc.message.feedback;

import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.ons.ctp.response.casesvc.representation.InboundChannel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseReceipt {
  protected String caseRef;
  protected String caseId;
  protected InboundChannel inboundChannel;
  protected XMLGregorianCalendar responseDateTime;
  protected String partyId;
}
