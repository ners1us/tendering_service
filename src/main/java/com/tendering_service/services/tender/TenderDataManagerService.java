package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.requests.TenderEditRequest;
import com.tendering_service.services.EntityDataManagerService;

public interface TenderDataManagerService extends EntityDataManagerService<TenderDto, TenderEditRequest> {
}
