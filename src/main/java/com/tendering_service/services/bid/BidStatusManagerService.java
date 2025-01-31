package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.services.EntityStatusService;

public interface BidStatusManagerService extends EntityStatusService<BidDto, BidStatus> {
}
