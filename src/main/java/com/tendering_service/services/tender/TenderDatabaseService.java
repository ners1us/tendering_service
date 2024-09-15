package com.tendering_service.services.tender;

import com.tendering_service.dto.TenderDto;

import java.util.List;

public interface TenderDatabaseService {
    List<TenderDto> getTendersByUsername(String username);

    List<TenderDto> getAllTenders();
}
