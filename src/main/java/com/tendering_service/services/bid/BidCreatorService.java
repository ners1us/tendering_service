package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;

public interface BidCreatorService {
    BidDto createBid(BidDto bidDto);
}
