package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;

public interface TenderCreatorService {
    TenderDto createTender(TenderDto tenderDto);
}
