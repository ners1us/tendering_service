package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;

import java.util.List;
import java.util.UUID;

public interface BidDatabaseService {
    List<BidDto> getBidsForTender(UUID tenderId, String username);

    List<BidDto> getBidsByUsername(String username);
}
