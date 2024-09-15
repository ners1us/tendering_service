package com.tendering_service.repositories;

import com.tendering_service.entities.Employee;
import com.tendering_service.entities.Organization;
import com.tendering_service.entities.OrganizationResponsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationResponsibleRepository extends JpaRepository<OrganizationResponsible, UUID> {
    Optional<OrganizationResponsible> findByUser(Employee employee);

    Optional<OrganizationResponsible> findByOrganization(Organization organization);
}
