package com.tendering_service.services.bid.implementations;

import com.tendering_service.utils.DataValidator;
import com.tendering_service.services.bid.BidDataManagerService;
import com.tendering_service.dto.BidDto;
import com.tendering_service.entities.Bid;
import com.tendering_service.entities.BidHistory;
import com.tendering_service.entities.Employee;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidHistoryRepository;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
import com.tendering_service.requests.BidEditRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidDataManagerServiceImpl implements BidDataManagerService {
    private final BidRepository bidRepository;

    private final BidHistoryRepository bidHistoryRepository;

    private final EmployeeRepository employeeRepository;

    public void incrementBidVersion(UUID bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        bid.setVersion(bid.getVersion() + 1);
        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        bidRepository.save(bid);

        saveBidHistory(bid);
    }

    public BidDto editBid(UUID bidId, String username, BidEditRequest bidEditRequest) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        DataValidator.checkIfUsernamesEqual(username, employee.getUsername());

        if (bidEditRequest.getName() != null) {
            bid.setName(bidEditRequest.getName());
        }
        if (bidEditRequest.getDescription() != null) {
            bid.setDescription(bidEditRequest.getDescription());
        }

        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Bid updatedBid = bidRepository.save(bid);

        return BidDto.fromEntity(updatedBid);
    }

    public BidDto rollbackBid(UUID bidId, Integer targetVersion, String username) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        BidHistory history = bidHistoryRepository.findByBidIdAndVersion(bidId, targetVersion)
                .orElseThrow(() -> new ResourceNotFoundException("Bid history not found"));

        DataValidator.checkIfIdsEqual(employee.getId(), bid.getAuthorId());

        bid.setName(history.getName());
        bid.setDescription(history.getDescription());
        bid.setVersion(history.getVersion());
        bid.setStatus(history.getStatus());
        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Bid updatedBid = bidRepository.save(bid);
        return BidDto.fromEntity(updatedBid);
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
