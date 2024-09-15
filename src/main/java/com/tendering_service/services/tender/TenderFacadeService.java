package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.requests.TenderEditRequest;

import java.util.List;
import java.util.UUID;

public interface TenderFacadeService {

    TenderDto createTender(TenderDto tender);

    TenderDto updateTenderStatus(UUID tenderId, TenderStatus status, String username);

    String getTenderStatus(UUID tenderId, String username);

    TenderDto rollbackTender(UUID tenderId, Integer targetVersion, String username);

    List<TenderDto> getTendersByUsername(String username);

    List<TenderDto> getAllTenders();

    TenderDto editTender(UUID tenderId, String username, TenderEditRequest tenderEditRequest);

    void incrementTenderVersion(UUID TenderId);
}
