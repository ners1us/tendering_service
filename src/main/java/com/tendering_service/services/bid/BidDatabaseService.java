package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.services.EntityDatabaseService;

import java.util.List;
import java.util.UUID;

public interface BidDatabaseService extends EntityDatabaseService<BidDto> {
    List<BidDto> getBidsForTender(UUID tenderId, String username);
}
