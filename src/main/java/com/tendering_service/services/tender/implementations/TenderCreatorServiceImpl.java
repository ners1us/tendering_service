package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.entities.Tender;
import com.tendering_service.entities.TenderHistory;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.EmployeeRepository;
import com.tendering_service.repositories.OrganizationRepository;
import com.tendering_service.repositories.TenderHistoryRepository;
import com.tendering_service.repositories.TenderRepository;
import com.tendering_service.services.tender.TenderCreatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class TenderCreatorServiceImpl implements TenderCreatorService {

    private final TenderRepository tenderRepository;

    private final TenderHistoryRepository tenderHistoryRepository;

    private final OrganizationRepository organizationRepository;

    private final EmployeeRepository employeeRepository;

    public TenderDto create(TenderDto tenderDto) {
        Tender tender = TenderDto.toEntity(tenderDto);

        if (organizationRepository.findById(tender.getOrganizationId()).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found");
        }

        if (employeeRepository.findByUsername(tender.getCreatorUsername()).isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        tender.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        tender.setStatus(TenderStatus.CREATED);
        tender.setVersion(1);
        Tender createdTender = tenderRepository.save(tender);

        TenderHistory tenderHistory = new TenderHistory();
        tenderHistory.setId(createdTender.getId());
        tenderHistory.setTender(createdTender);
        tenderHistory.setName(createdTender.getName());
        tenderHistory.setDescription(createdTender.getDescription());
        tenderHistory.setOrganizationId(createdTender.getOrganizationId());
        tenderHistory.setServiceType(createdTender.getServiceType());
        tenderHistory.setStatus(createdTender.getStatus());
        tenderHistory.setVersion(createdTender.getVersion());
        tenderHistory.setCreatorUsername(createdTender.getCreatorUsername());

        tenderHistoryRepository.save(tenderHistory);

        return TenderDto.fromEntity(createdTender);
    }
}
