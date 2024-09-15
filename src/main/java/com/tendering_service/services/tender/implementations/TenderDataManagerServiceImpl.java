package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.entities.Tender;
import com.tendering_service.entities.TenderHistory;
import com.tendering_service.enums.ServiceType;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.TenderHistoryRepository;
import com.tendering_service.repositories.TenderRepository;
import com.tendering_service.requests.TenderEditRequest;
import com.tendering_service.services.tender.TenderDataManagerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TenderDataManagerServiceImpl implements TenderDataManagerService {

    private final TenderRepository tenderRepository;

    private final TenderHistoryRepository tenderHistoryRepository;

    public TenderDto rollbackTender(UUID tenderId, Integer targetVersion, String username) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found"));
        TenderHistory history = tenderHistoryRepository.findByTenderIdAndVersion(tenderId, targetVersion)
                .orElseThrow(() -> new ResourceNotFoundException("TenderHistory not found"));

        if (!tender.getCreatorUsername().equals(username)) {
            throw new SecurityException("You do not have permission to rollback this tender");
        }

        tender.setName(history.getName());
        tender.setDescription(history.getDescription());
        tender.setVersion(history.getVersion());
        tender.setStatus(history.getStatus());
        tender.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Tender updatedTender = tenderRepository.save(tender);

        return TenderDto.fromEntity(updatedTender);
    }

    public void incrementTenderVersion(UUID tenderId) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found"));

        tender.setVersion(tender.getVersion() + 1);
        tender.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        tenderRepository.save(tender);

        TenderHistory tenderHistory = new TenderHistory();
        tenderHistory.setTender(tender);
        tenderHistory.setName(tender.getName());
        tenderHistory.setDescription(tender.getDescription());
        tenderHistory.setOrganizationId(tender.getOrganizationId());
        tenderHistory.setServiceType(tender.getServiceType());
        tenderHistory.setStatus(tender.getStatus());
        tenderHistory.setVersion(tender.getVersion());
        tenderHistory.setCreatorUsername(tender.getCreatorUsername());

        tenderHistoryRepository.save(tenderHistory);
    }

    public TenderDto editTender(UUID tenderId, String username, TenderEditRequest tenderEditRequest) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found"));

        if (!tender.getCreatorUsername().equals(username)) {
            throw new SecurityException("You do not have permission to edit this tender");
        }

        if (tenderEditRequest.getName() != null) {
            tender.setName(tenderEditRequest.getName());
        }
        if (tenderEditRequest.getDescription() != null) {
            tender.setDescription(tenderEditRequest.getDescription());
        }
        if (tenderEditRequest.getServiceType() != null) {
            tender.setServiceType(ServiceType.valueOf(tenderEditRequest.getServiceType()));
        }

        tender.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Tender updatedTender = tenderRepository.save(tender);

        return TenderDto.fromEntity(updatedTender);
    }
}
