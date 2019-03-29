package uk.gov.ons.ctp.response.casesvc.domain.dto;

import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseReceipt {
  private String caseRef;
  private String caseId;
  private InboundChannel inboundChannel;
  private XMLGregorianCalendar responseDateTime;
  private String partyId;
}
