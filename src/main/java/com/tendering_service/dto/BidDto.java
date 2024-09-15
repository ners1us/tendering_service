package com.tendering_service.dto;

import com.tendering_service.entities.Bid;
import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class BidDto {

    private UUID id;

    private String name;

    private String description;

    private UUID tender;

    private UUID organizationId;

    private AuthorType authorType;

    private BidStatus status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Integer version;

    private UUID authorId;

    public static BidDto fromEntity(Bid bid) {
        BidDto bidDto = new BidDto();

        bidDto.setId(bid.getId());
        bidDto.setName(bid.getName());
        bidDto.setDescription(bid.getDescription());
        bidDto.setTender(bid.getTenderId());
        bidDto.setOrganizationId(bid.getOrganizationId());
        bidDto.setAuthorType(bid.getAuthorType());
        bidDto.setStatus(bid.getStatus());
        bidDto.setCreatedAt(bid.getCreatedAt());
        bidDto.setUpdatedAt(bid.getUpdatedAt());
        bidDto.setVersion(bid.getVersion());
        bidDto.setAuthorId(bid.getAuthorId());

        return bidDto;
    }

    public static Bid toEntity(BidDto bidDto) {
        Bid bid = new Bid();

        bid.setId(bidDto.getId());
        bid.setName(bidDto.getName());
        bid.setDescription(bidDto.getDescription());
        bid.setTenderId(bidDto.getTender());
        bid.setOrganizationId(bidDto.getOrganizationId());
        bid.setAuthorType(bidDto.getAuthorType());
        bid.setStatus(bidDto.getStatus());
        bid.setCreatedAt(bidDto.getCreatedAt() == null ? new Timestamp(System.currentTimeMillis()) : bidDto.getCreatedAt());
        bid.setUpdatedAt(bidDto.getUpdatedAt() == null ? new Timestamp(System.currentTimeMillis()) : bidDto.getUpdatedAt());
        bid.setVersion(bidDto.getVersion());
        bid.setAuthorId(bidDto.getAuthorId());

        return bid;
    }

    public static List<BidDto> fromEntities(List<Bid> bids) {
        return bids.stream()
                .map(BidDto::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<Bid> toEntities(List<BidDto> bidDtos) {
        return bidDtos.stream()
                .map(BidDto::toEntity)
                .collect(Collectors.toList());
    }
}
