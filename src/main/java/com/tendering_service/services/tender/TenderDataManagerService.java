package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.requests.TenderEditRequest;

import java.util.UUID;

public interface TenderDataManagerService {
    TenderDto rollbackTender(UUID tenderId, Integer targetVersion, String username);

    void incrementTenderVersion(UUID tenderId);

    TenderDto editTender(UUID tenderId, String username, TenderEditRequest tenderEditRequest);
}
