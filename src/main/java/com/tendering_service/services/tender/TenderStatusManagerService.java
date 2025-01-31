package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.services.EntityStatusService;

public interface TenderStatusManagerService extends EntityStatusService<TenderDto, TenderStatus> {
}
