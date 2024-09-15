package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.entities.Tender;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.TenderRepository;
import com.tendering_service.services.tender.TenderStatusManagerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TenderStatusManagerServiceImpl implements TenderStatusManagerService {

    private final TenderRepository tenderRepository;

    public TenderDto updateTenderStatus(UUID tenderId, TenderStatus status, String username) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found"));

        if (!tender.getCreatorUsername().equals(username)) {
            throw new SecurityException("You do not have permission to update this tender's status");
        }

        tender.setStatus(status);
        tender.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Tender updatedTender = tenderRepository.save(tender);

        return TenderDto.fromEntity(updatedTender);
    }

    public String getTenderStatus(UUID tenderId, String username) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found"));

        if (!tender.getCreatorUsername().equals(username)) {
            return "User does not have permission to view this tender status";
        }

        return tender.getStatus().name();
    }
}

