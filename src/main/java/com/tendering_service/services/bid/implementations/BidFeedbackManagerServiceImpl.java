package com.tendering_service.services.bid.implementations;

import com.tendering_service.services.other.DataValidator;
import com.tendering_service.services.bid.BidFeedbackManagerService;
import com.tendering_service.entities.Bid;
import com.tendering_service.entities.BidHistory;
import com.tendering_service.entities.Employee;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidHistoryRepository;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidFeedbackManagerServiceImpl implements BidFeedbackManagerService {

    private BidRepository bidRepository;

    private EmployeeRepository employeeRepository;

    private BidHistoryRepository bidHistoryRepository;

    public void submitFeedback(UUID bidId, String feedback, String username) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        BidHistory history = bidHistoryRepository.findByBidIdAndVersion(bidId, bid.getVersion())
                .orElseThrow(() -> new ResourceNotFoundException("Bid history not found"));

        DataValidator.checkIfIdsEqual(employee.getId(), bid.getAuthorId());

        bid.setFeedback(feedback);
        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        bidRepository.save(bid);

        history.setFeedback(feedback);
        history.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        bidHistoryRepository.save(history);
    }
}
