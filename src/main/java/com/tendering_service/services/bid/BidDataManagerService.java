package com.tendering_service.services.bid;

import com.tendering_service.dto.BidDto;
import com.tendering_service.requests.BidEditRequest;
import com.tendering_service.services.EntityDataManagerService;

public interface BidDataManagerService extends EntityDataManagerService<BidDto, BidEditRequest> {
}
