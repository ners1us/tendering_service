package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.TenderStatus;

import java.util.UUID;

public interface TenderStatusManagerService {
    TenderDto updateTenderStatus(UUID tenderId, TenderStatus status, String username);

    String getTenderStatus(UUID tenderId, String username);
}
