package com.tendering_service.services.bid.implementations;

import com.tendering_service.services.bid.BidDatabaseService;
import com.tendering_service.dto.BidDto;
import com.tendering_service.entities.Bid;
import com.tendering_service.entities.Employee;
import com.tendering_service.exceptions.ResourceNotFoundException;
import com.tendering_service.repositories.BidRepository;
import com.tendering_service.repositories.EmployeeRepository;
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
        List<Bid> bids = bidRepository.findByTenderId(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        return bids.stream()
                .map(BidDto::fromEntity)
                .filter(bid -> employee.getId().equals(bid.getAuthorId()))
                .collect(Collectors.toList());
    }

    public List<BidDto> getItemsByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Bid> bids = bidRepository.findByAuthorId(employee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bids not found"));

        return bids.stream()
                .map(BidDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<BidDto> getAllItems() {
        return bidRepository.findAll().stream()
                .map(BidDto::fromEntity)
                .collect(Collectors.toList());
    }
}
