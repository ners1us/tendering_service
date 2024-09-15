package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.requests.BidEditRequest;
import com.tendering_service.services.bid.implementations.BidFacadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidFacadeServiceTests {

    @Mock
    private BidDatabaseService bidDatabaseService;

    @Mock
    private BidCreatorService bidCreatorService;

    @Mock
    private BidStatusManagerService bidStatusManagerService;

    @Mock
    private BidDataManagerService bidDataManagerService;

    @Mock
    private BidFeedbackManagerService bidFeedbackManagerService;

    @Mock
    private BidDecisionManagerService bidDecisionManagerService;

    @InjectMocks
    private BidFacadeServiceImpl bidFacadeService;

    private BidDto bidDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bidDto = new BidDto();
        bidDto.setAuthorId(UUID.randomUUID());
        bidDto.setTender(UUID.randomUUID());
        bidDto.setAuthorType(AuthorType.User);
        bidDto.setName("Test Bid");
        bidDto.setDescription("Test Description");
    }

    @Test
    void createBidSuccessTest() {
        when(bidCreatorService.createBid(any(BidDto.class))).thenReturn(bidDto);

        BidDto createdBid = bidFacadeService.createBid(bidDto);

        assertNotNull(createdBid);
        assertEquals("Test Bid", createdBid.getName());
        assertEquals("Test Description", createdBid.getDescription());
        verify(bidCreatorService, times(1)).createBid(any(BidDto.class));
    }

    @Test
    void updateBidStatusThrowsResourceNotFoundExceptionTest() {
        when(bidStatusManagerService.updateBidStatus(any(UUID.class), any(BidStatus.class), anyString()))
                .thenThrow(new ResourceNotFoundException("Bid not found"));

        assertThrows(ResourceNotFoundException.class, () -> bidFacadeService.updateBidStatus(UUID.randomUUID(), BidStatus.PUBLISHED, "username"));

        verify(bidStatusManagerService, times(1)).updateBidStatus(any(UUID.class), any(BidStatus.class), anyString());
    }

    @Test
    void getBidStatusTest() {
        when(bidStatusManagerService.getBidStatus(any(UUID.class), anyString())).thenReturn(BidStatus.CREATED.name());

        String status = bidFacadeService.getBidStatus(UUID.randomUUID(), "username");

        assertEquals(BidStatus.CREATED.name(), status);
        verify(bidStatusManagerService, times(1)).getBidStatus(any(UUID.class), anyString());
    }

    @Test
    void getBidsForTenderTest() {
        List<BidDto> bidList = List.of(bidDto);
        when(bidDatabaseService.getBidsForTender(any(UUID.class), anyString())).thenReturn(bidList);

        List<BidDto> bids = bidFacadeService.getBidsForTender(UUID.randomUUID(), "username");

        assertNotNull(bids);
        assertEquals(1, bids.size());
        verify(bidDatabaseService, times(1)).getBidsForTender(any(UUID.class), anyString());
    }

    @Test
    void rollbackBidTest() {
        when(bidDataManagerService.rollbackBid(any(UUID.class), anyInt(), anyString())).thenReturn(bidDto);

        BidDto rolledBackBid = bidFacadeService.rollbackBid(UUID.randomUUID(), 1, "username");

        assertNotNull(rolledBackBid);
        assertEquals(bidDto.getName(), rolledBackBid.getName());
        verify(bidDataManagerService, times(1)).rollbackBid(any(UUID.class), anyInt(), anyString());
    }

    @Test
    void getBidsByUsernameTest() {
        List<BidDto> bidList = List.of(bidDto);
        when(bidDatabaseService.getBidsByUsername(anyString())).thenReturn(bidList);

        List<BidDto> bids = bidFacadeService.getBidsByUsername("username");

        assertNotNull(bids);
        assertEquals(1, bids.size());
        verify(bidDatabaseService, times(1)).getBidsByUsername(anyString());
    }

    @Test
    void editBidTest() {
        BidEditRequest bidEditRequest = new BidEditRequest();
        bidEditRequest.setName("Edited Bid");

        BidDto editedBid = new BidDto();
        editedBid.setName("Edited Bid");

        when(bidDataManagerService.editBid(any(UUID.class), anyString(), any(BidEditRequest.class))).thenReturn(editedBid);

        BidDto result = bidFacadeService.editBid(UUID.randomUUID(), "username", bidEditRequest);

        assertNotNull(result);
        assertEquals("Edited Bid", result.getName());
        verify(bidDataManagerService, times(1)).editBid(any(UUID.class), anyString(), any(BidEditRequest.class));
    }

    @Test
    void incrementBidVersionTest() {
        doNothing().when(bidDataManagerService).incrementBidVersion(any(UUID.class));

        bidFacadeService.incrementBidVersion(UUID.randomUUID());

        verify(bidDataManagerService, times(1)).incrementBidVersion(any(UUID.class));
    }

    @Test
    void submitDecisionTest() {
        UUID bidId = UUID.randomUUID();
        DecisionStatus decisionStatus = DecisionStatus.Approved;
        String username = "username";

        doNothing().when(bidDecisionManagerService).submitDecision(bidId, decisionStatus, username);

        bidFacadeService.submitDecision(bidId, decisionStatus, username);

        verify(bidDecisionManagerService, times(1)).submitDecision(bidId, decisionStatus, username);
    }

    @Test
    void submitFeedbackTest() {
        UUID bidId = UUID.randomUUID();
        String feedback = "This is feedback";
        String username = "username";

        doNothing().when(bidFeedbackManagerService).submitFeedback(bidId, feedback, username);

        bidFacadeService.submitFeedback(bidId, feedback, username);

        verify(bidFeedbackManagerService, times(1)).submitFeedback(bidId, feedback, username);
    }

}
