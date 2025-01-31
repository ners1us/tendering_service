package com.tendering_service.services.bid.implementations;

import com.tendering_service.services.bid.BidCreatorService;
import com.tendering_service.dto.BidDto;
import com.tendering_service.entities.*;
import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class BidCreatorServiceImpl implements BidCreatorService {
    private final BidRepository bidRepository;

    private final OrganizationResponsibleRepository organizationResponsibleRepository;

    private final EmployeeRepository employeeRepository;

    private final OrganizationRepository organizationRepository;

    private final BidHistoryRepository bidHistoryRepository;

    private final TenderRepository tenderRepository;

    public BidDto create(BidDto bidDto) {
        Bid bid = BidDto.toEntity(bidDto);

        if (tenderRepository.findById(bid.getTenderId()).isEmpty()) {
            throw new ResourceNotFoundException("Tender Not Found");
        }

        bid.setStatus(BidStatus.CREATED);
        bid.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        bid.setVersion(1);

        if (bid.getAuthorType().equals(AuthorType.User)) {
            Employee employee = employeeRepository.findById(bid.getAuthorId()).get();

            OrganizationResponsible organizationResponsible = organizationResponsibleRepository.findByUser(employee)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            bid.setOrganizationId(organizationResponsible.getOrganization().getId());
        } else if (bid.getAuthorType().equals(AuthorType.Organization)) {
            Organization organization = organizationRepository.findById(bid.getAuthorId()).get();

            bid.setOrganizationId(organization.getId());
        }

        Bid createdBid = bidRepository.save(bid);
        saveBidHistory(createdBid);

        return BidDto.fromEntity(createdBid);
    }

    private void saveBidHistory(Bid bid) {
        BidHistory bidHistory = new BidHistory();
        bidHistory.setBid(bid);
        bidHistory.setName(bid.getName());
        bidHistory.setDescription(bid.getDescription());
        bidHistory.setOrganizationId(bid.getOrganizationId());
        bidHistory.setAuthorType(bid.getAuthorType());
        bidHistory.setStatus(bid.getStatus());
        bidHistory.setVersion(bid.getVersion());
        bidHistory.setAuthorId(bid.getAuthorId());

        bidHistoryRepository.save(bidHistory);
    }
}
