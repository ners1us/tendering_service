package com.tendering_service.repositories;

import com.tendering_service.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByTenderId(UUID tenderId);

    List<Bid> findByAuthorId(UUID authorId);
}

