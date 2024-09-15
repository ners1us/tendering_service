package com.tendering_service.services.bid.implementations;

import com.tendering_service.dto.BidDto;
import com.tendering_service.entities.Bid;
import com.tendering_service.entities.Employee;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
import com.tendering_service.services.bid.BidDatabaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BidDatabaseServiceImpl implements BidDatabaseService {
    private final BidRepository bidRepository;

    private final EmployeeRepository employeeRepository;

    public List<BidDto> getBidsForTender(UUID tenderId, String username) {
        List<Bid> bids = bidRepository.findByTenderId(tenderId);
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        return bids.stream()
                .map(BidDto::fromEntity)
                .filter(bid -> employee.getId().equals(bid.getAuthorId()))
                .collect(Collectors.toList());
    }

    public List<BidDto> getBidsByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Bid> bids = bidRepository.findByAuthorId(employee.getId());

        return bids.stream()
                .map(BidDto::fromEntity)
                .collect(Collectors.toList());
    }
}
