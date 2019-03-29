package uk.gov.ons.ctp.response.casesvc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.ons.ctp.common.FixtureHelper;
import uk.gov.ons.ctp.common.state.StateTransitionManager;
import uk.gov.ons.ctp.response.casesvc.client.CollectionExerciseSvcClient;
import uk.gov.ons.ctp.response.casesvc.domain.dto.CaseGroupStatus;
import uk.gov.ons.ctp.response.casesvc.domain.dto.CaseState;
import uk.gov.ons.ctp.response.casesvc.domain.dto.SampleUnitParent;
import uk.gov.ons.ctp.response.casesvc.domain.model.Case;
import uk.gov.ons.ctp.response.casesvc.domain.model.CaseEvent;
import uk.gov.ons.ctp.response.casesvc.domain.model.CaseGroup;
import uk.gov.ons.ctp.response.casesvc.domain.repository.CaseEventRepository;
import uk.gov.ons.ctp.response.casesvc.domain.repository.CaseGroupRepository;
import uk.gov.ons.ctp.response.casesvc.domain.repository.CaseIacAuditRepository;
import uk.gov.ons.ctp.response.casesvc.domain.repository.CaseRepository;
import uk.gov.ons.ctp.response.casesvc.message.CaseNotificationPublisher;
import uk.gov.ons.ctp.response.casesvc.message.EventPublisher;
import uk.gov.ons.ctp.response.casesvc.utility.IacDispenser;
import uk.gov.ons.ctp.response.collection.exercise.representation.CollectionExerciseDTO;
import uk.gov.ons.ctp.response.sample.representation.SampleUnitDTO;
import uk.gov.ons.ctp.response.sample.representation.SampleUnitDTO.SampleUnitType;

/** Test Case created by Sample */
@RunWith(MockitoJUnitRunner.class)
public class CaseCreationServiceTest {

  @InjectMocks private CaseService caseService;

  @Mock private CaseRepository caseRepo;
  @Mock private CaseGroupRepository caseGroupRepo;
  @Mock private CaseEventRepository caseEventRepo;
  @Mock private CollectionExerciseSvcClient collectionExerciseSvcClient;
  @Mock private IacDispenser iacDispenser;
  @Mock private EventPublisher eventPublisher;
  @Mock private StateTransitionManager<CaseState, CaseEvent> caseSvcStateTransitionManager;
  @Mock private CaseIacAuditRepository caseIacAuditRepo;
  @Mock private CaseNotificationPublisher notificationPublisher;

  /**
   * Create a Case and a Casegroup from the message that would be on the Case Delivery Queue. No
   * child party.
   */
  @Test
  public void testCreateCaseAndCaseGroupWithoutChildFromMessage() throws Exception {

    SampleUnitParent sampleUnitParent = new SampleUnitParent();

    // Parent Only field
    sampleUnitParent.setCollectionExerciseId("14fb3e68-4dca-46db-bf49-04b84e07e77c");
    // Base sample unit data
    sampleUnitParent.setActionPlanId("7bc5d41b-0549-40b3-ba76-42f6d4cf3991");
    sampleUnitParent.setCollectionInstrumentId("8bae64c5-a282-4e87-ae5d-cd4181ba6c73");
    sampleUnitParent.setPartyId("7bc5d41b-0549-40b3-ba76-42f6d4cf3992");
    sampleUnitParent.setSampleUnitRef("str1234");
    sampleUnitParent.setSampleUnitType("B");
    sampleUnitParent.setId(UUID.randomUUID().toString());
    List<CollectionExerciseDTO> collectionExercises =
        FixtureHelper.loadClassFixtures(CollectionExerciseDTO[].class);
    when(collectionExerciseSvcClient.getCollectionExercise(any()))
        .thenReturn(collectionExercises.get(0));

    Case updatedCase = new Case();
    updatedCase.setCasePK(999);
    updatedCase.setId(UUID.randomUUID());
    updatedCase.setActionPlanId(UUID.randomUUID());
    updatedCase.setSampleUnitType(SampleUnitType.H);
    when(caseRepo.save(any(Case.class))).thenReturn(updatedCase);

    CaseGroup imaginaryCaseGroup = new CaseGroup();
    imaginaryCaseGroup.setCollectionExerciseId(UUID.randomUUID());
    when(caseGroupRepo.findOne(any(Integer.class))).thenReturn(imaginaryCaseGroup);

    when(caseSvcStateTransitionManager.transition(any(), any())).thenReturn(CaseState.ACTIONABLE);

    caseService.createInitialCase(sampleUnitParent);

    // Both CaseGroup and Case attributes created from parent sample unit
    ArgumentCaptor<CaseGroup> caseGroup = ArgumentCaptor.forClass(CaseGroup.class);
    verify(caseGroupRepo, times(1)).save(caseGroup.capture());
    CaseGroup capturedCaseGroup = caseGroup.getValue();

    assertEquals(UUID.class, capturedCaseGroup.getId().getClass());
    assertEquals(
        UUID.fromString(sampleUnitParent.getCollectionExerciseId()),
        capturedCaseGroup.getCollectionExerciseId());
    assertEquals(UUID.fromString(sampleUnitParent.getPartyId()), capturedCaseGroup.getPartyId());
    assertEquals(sampleUnitParent.getSampleUnitRef(), capturedCaseGroup.getSampleUnitRef());
    assertEquals(sampleUnitParent.getSampleUnitType(), capturedCaseGroup.getSampleUnitType());
    assertEquals(CaseGroupStatus.NOTSTARTED, capturedCaseGroup.getStatus());

    ArgumentCaptor<Case> caze = ArgumentCaptor.forClass(Case.class);
    verify(caseRepo, times(2)).save(caze.capture());
    Case capturedCase = caze.getAllValues().get(0);

    assertEquals(UUID.class, capturedCase.getId().getClass());
    assertEquals(new Integer(capturedCaseGroup.getCaseGroupPK()), capturedCase.getCaseGroupFK());
    assertEquals(capturedCaseGroup.getId(), capturedCase.getCaseGroupId());
    assertEquals(CaseState.ACTIONABLE, capturedCase.getState());
    assertEquals(
        SampleUnitDTO.SampleUnitType.valueOf(sampleUnitParent.getSampleUnitType()),
        capturedCase.getSampleUnitType());
    assertEquals(UUID.fromString(sampleUnitParent.getPartyId()), capturedCase.getPartyId());
    assertEquals(
        UUID.fromString(sampleUnitParent.getCollectionInstrumentId()),
        capturedCase.getCollectionInstrumentId());
    assertEquals(
        UUID.fromString(sampleUnitParent.getActionPlanId()), capturedCase.getActionPlanId());
  }
}
