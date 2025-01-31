package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;

    public List<TenderDto> getItemsByUsername(String username) {
        if (employeeRepository.findByUsername(username).isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        return tenderRepository.findByCreatorUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Bids not found"))
                .stream()
                .map(TenderDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TenderDto> getAllItems() {
        return tenderRepository.findAll().stream()
                .map(TenderDto::fromEntity)
                .collect(Collectors.toList());
    }
}
