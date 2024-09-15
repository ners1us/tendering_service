package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.repositories.TenderRepository;
import com.tendering_service.services.tender.TenderDatabaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TenderDatabaseServiceImpl implements TenderDatabaseService {

    private final TenderRepository tenderRepository;

    public List<TenderDto> getTendersByUsername(String username) {
        return tenderRepository.findByCreatorUsername(username).stream()
                .map(TenderDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TenderDto> getAllTenders() {
        return tenderRepository.findAll().stream()
                .map(TenderDto::fromEntity)
                .collect(Collectors.toList());
    }
}
