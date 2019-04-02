package uk.gov.ons.ctp.response.casesvc;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import javax.xml.bind.JAXBContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.utility.Mapzer;
import uk.gov.ons.ctp.response.casesvc.config.AppConfig;
import uk.gov.ons.ctp.response.casesvc.domain.model.Case;
import uk.gov.ons.ctp.response.casesvc.message.notification.CaseNotification;
import uk.gov.ons.ctp.response.casesvc.message.sampleunitnotification.SampleUnitParent;
import uk.gov.ons.tools.rabbit.Rabbitmq;
import uk.gov.ons.tools.rabbit.SimpleMessageBase;
import uk.gov.ons.tools.rabbit.SimpleMessageListener;
import uk.gov.ons.tools.rabbit.SimpleMessageSender;

@Component
public class CaseCreator {

  private static final Logger log = LoggerFactory.getLogger(CaseCreator.class);

  @Autowired private AppConfig appConfig;
  @Autowired private ResourceLoader resourceLoader;
  @Autowired private IACServiceStub iacServiceStub;
  @Autowired private ObjectMapper objectMapper;

  /**
   * Sends a sample unit in a message so that casesvc creates a case, then waits for a message on
   * the case lifecycle queue which confirms case creation
   *
   * @return a new CaseNotification
   */
  public CaseNotification sendSampleUnit(
      String sampleUnitRef, String sampleUnitType, UUID sampleUnitId, UUID collectionExerciseId)
      throws Exception {

    iacServiceStub.createIACStub();

    SampleUnitParent sampleUnitParent = new SampleUnitParent();
    sampleUnitParent.setCollectionExerciseId(collectionExerciseId.toString());
    sampleUnitParent.setId(sampleUnitId.toString());
    sampleUnitParent.setActionPlanId(UUID.randomUUID().toString());
    sampleUnitParent.setSampleUnitRef(sampleUnitRef);
    sampleUnitParent.setCollectionInstrumentId(UUID.randomUUID().toString());
    sampleUnitParent.setPartyId(UUID.randomUUID().toString());
    sampleUnitParent.setSampleUnitType(sampleUnitType);

    JAXBContext jaxbContext = JAXBContext.newInstance(SampleUnitParent.class);

    String json = objectMapper.writeValueAsString(sampleUnitParent);

//    String xml =
//            new Mapzer(resourceLoader)
//                .convertObjectToXml(
//                    jaxbContext, sampleUnit, "casesvc/xsd/inbound/SampleUnitNotification.xsd");
//

    BlockingQueue<String> queue =
        getMessageListener()
            .listen(
                SimpleMessageBase.ExchangeType.Direct,
                "case-outbound-exchange",
                "Case.LifecycleEvents.binding");

    //Temp, to see if this is the issue
//    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:sampleUnitParent xmlns:ns2=\"http://ons.gov.uk/ctp/response/casesvc/message/sampleunitnotification\"><id>db0467b5-2f4b-4093-9a6b-0dc494b4306a</id><sampleUnitRef>LMS0003</sampleUnitRef><sampleUnitType>H</sampleUnitType><partyId>ea684244-1401-4ad0-a46d-96bcbaba1693</partyId><collectionInstrumentId>d48b7219-70bc-4090-9a14-df20b729aad5</collectionInstrumentId><actionPlanId>33978fd1-7c32-471d-9cc9-f6340cfcb13d</actionPlanId><collectionExerciseId>90f43060-1a8c-4869-b001-0b00ced09fe2</collectionExerciseId></ns2:sampleUnitParent>";

    getMessageSender().sendMessage("collection-inbound-exchange", "Case.CaseDelivery.binding", json);

    String message = waitForNotification(queue);

    CaseNotification caseNotification = objectMapper.readValue(message, CaseNotification.class);

    return caseNotification;

//    jaxbContext = JAXBContext.newInstance(CaseNotification.class);
//    return (CaseNotification)
//        jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(message.getBytes()));
  }

  private String waitForNotification(BlockingQueue<String> queue) throws Exception {
    String message = queue.take();
    log.info("message = " + message);
    assertNotNull("Timeout waiting for message to arrive in Case.LifecycleEvents", message);

    return message;
  }

  /**
   * Creates a new SimpleMessageSender based on the config in AppConfig
   *
   * @return a new SimpleMessageSender
   */
  private SimpleMessageSender getMessageSender() {
    Rabbitmq config = this.appConfig.getRabbitmq();

    return new SimpleMessageSender(
        config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
  }

  /**
   * Creates a new SimpleMessageListener based on the config in AppConfig
   *
   * @return a new SimpleMessageListener
   */
  private SimpleMessageListener getMessageListener() {
    Rabbitmq config = this.appConfig.getRabbitmq();

    return new SimpleMessageListener(
        config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
  }
}
