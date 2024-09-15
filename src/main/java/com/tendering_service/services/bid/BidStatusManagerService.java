package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.BidStatus;

import java.util.UUID;

public interface BidStatusManagerService {
    BidDto updateBidStatus(UUID bidId, BidStatus status, String username);

    String getBidStatus(UUID bidId, String username);
}
