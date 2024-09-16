package com.tendering_service.repositories;

import com.tendering_service.entities.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenderRepository extends JpaRepository<Tender, UUID> {
    Optional<List<Tender>> findByCreatorUsername(String username);
}
