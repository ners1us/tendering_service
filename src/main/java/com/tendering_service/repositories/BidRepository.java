package com.tendering_service.repositories;

import com.tendering_service.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    Optional<List<Bid>> findByTenderId(UUID tenderId);

    Optional<List<Bid>> findByAuthorId(UUID authorId);
}

