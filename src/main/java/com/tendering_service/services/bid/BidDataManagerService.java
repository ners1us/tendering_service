package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.requests.BidEditRequest;

import java.util.UUID;

public interface BidDataManagerService {
    void incrementBidVersion(UUID bidId);

    BidDto editBid(UUID bidId, String username, BidEditRequest bidEditRequest);

    BidDto rollbackBid(UUID bidId, Integer targetVersion, String username);
}
