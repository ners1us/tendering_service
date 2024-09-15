package com.tendering_service.repositories;

import com.tendering_service.entities.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidHistoryRepository extends JpaRepository<BidHistory, UUID> {
    Optional<BidHistory> findByBidIdAndVersion(UUID bidId, Integer version);
}
