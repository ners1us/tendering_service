package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import com.tendering_service.requests.BidEditRequest;

import java.util.List;
import java.util.UUID;

public interface BidFacadeService {
    BidDto createBid(BidDto bidDto);

    List<BidDto> getBidsForTender(UUID tenderId, String username);

    BidDto updateBidStatus(UUID bidId, BidStatus status, String username);

    String getBidStatus(UUID bidId, String username);

    BidDto rollbackBid(UUID bidId, Integer targetVersion, String username);

    List<BidDto> getBidsByUsername(String username);

    BidDto editBid(UUID bidId, String username, BidEditRequest bidEditRequest);

    void incrementBidVersion(UUID bidId);

    void submitDecision(UUID bidId, DecisionStatus status, String username);

    void submitFeedback(UUID bidId, String feedback, String username);
}
