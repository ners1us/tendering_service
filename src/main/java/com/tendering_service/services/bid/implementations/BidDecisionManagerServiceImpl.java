package com.tendering_service.services.bid.implementations;

import com.tendering_service.entities.Bid;
import com.tendering_service.entities.BidHistory;
import com.tendering_service.entities.Employee;
import com.tendering_service.enums.DecisionStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidHistoryRepository;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
import com.tendering_service.services.bid.BidDecisionManagerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidDecisionManagerServiceImpl implements BidDecisionManagerService {

    private BidRepository bidRepository;

    private EmployeeRepository employeeRepository;

    private BidHistoryRepository bidHistoryRepository;

    public void submitDecision(UUID bidId, DecisionStatus decision, String username) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        BidHistory history = bidHistoryRepository.findByBidIdAndVersion(bidId, bid.getVersion())
                .orElseThrow(() -> new ResourceNotFoundException("Bid history not found"));

        if (!bid.getAuthorId().equals(employee.getId())) {
            throw new SecurityException("You do not have permission to edit this bid");
        }

        bid.setDecision(decision);
        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        bidRepository.save(bid);

        history.setDecision(decision);
        history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        bidHistoryRepository.save(history);
    }
}
