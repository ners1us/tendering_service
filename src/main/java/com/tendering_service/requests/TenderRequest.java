package com.tendering_service.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class TenderRequest {
    private String name;

    private String description;

    private String serviceType;

    private UUID organizationId;

    private String creatorUsername;
}
