package com.tendering_service.dto;

import com.tendering_service.entities.Tender;
import com.tendering_service.enums.ServiceType;
import com.tendering_service.enums.TenderStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class TenderDto {

    private UUID id;

    private String name;

    private String description;

    private UUID organization;

    private ServiceType serviceType;

    private TenderStatus status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Integer version;

    private String creatorUsername;

    public static TenderDto fromEntity(Tender tender) {
        TenderDto tenderDto = new TenderDto();

        tenderDto.setId(tender.getId());
        tenderDto.setName(tender.getName());
        tenderDto.setDescription(tender.getDescription());
        tenderDto.setOrganization(tender.getOrganizationId());
        tenderDto.setServiceType(tender.getServiceType());
        tenderDto.setStatus(tender.getStatus());
        tenderDto.setCreatedAt(tender.getCreatedAt());
        tenderDto.setUpdatedAt(tender.getUpdatedAt());
        tenderDto.setVersion(tender.getVersion());
        tenderDto.setCreatorUsername(tender.getCreatorUsername());

        return tenderDto;
    }

    public static Tender toEntity(TenderDto tenderDto) {
        Tender tender = new Tender();

        tender.setId(tenderDto.getId());
        tender.setName(tenderDto.getName());
        tender.setDescription(tenderDto.getDescription());
        tender.setOrganizationId(tenderDto.getOrganization());
        tender.setServiceType(tenderDto.getServiceType());
        tender.setStatus(tenderDto.getStatus());
        tender.setCreatedAt(tenderDto.getCreatedAt() == null ? new Timestamp(System.currentTimeMillis()) : tenderDto.getCreatedAt());
        tender.setUpdatedAt(tenderDto.getUpdatedAt() == null ? new Timestamp(System.currentTimeMillis()) : tenderDto.getUpdatedAt());
        tender.setVersion(tenderDto.getVersion());
        tender.setCreatorUsername(tenderDto.getCreatorUsername());

        return tender;
    }

    public static List<TenderDto> fromEntities(List<Tender> tenders) {
        return tenders.stream()
                .map(TenderDto::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<Tender> toEntities(List<TenderDto> tenderDtos) {
        return tenderDtos.stream()
                .map(TenderDto::toEntity)
                .collect(Collectors.toList());
    }
}
