package com.tendering_service.services.bid.implementations;

import com.tendering_service.services.bid.BidStatusManagerService;
import com.tendering_service.dto.BidDto;
import com.tendering_service.entities.Bid;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BidStatusManagerServiceImpl implements BidStatusManagerService {
    private final BidRepository bidRepository;

    private final EmployeeRepository employeeRepository;

    public BidDto updateBidStatus(UUID bidId, BidStatus status, String username) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        if (!username.isEmpty()) {
            if (employeeRepository.findByUsername(username).isEmpty()) {
                throw new SecurityException("You do not have permission to update bid's status");
            }
        }

        bid.setStatus(status);
        bid.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Bid updatedBid = bidRepository.save(bid);

        return BidDto.fromEntity(updatedBid);
    }

    public String getBidStatus(UUID bidId, String username) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

        if (username != null && !username.isEmpty()) {
            if (employeeRepository.findByUsername(username).isEmpty()) {
                return "User does not have permission to view this bid status";
            }
        }

        return bid.getStatus().name();
    }
}
