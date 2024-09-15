package com.tendering_service.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class BidRequest {
    private String name;

    private String description;

    private String authorType;

    private UUID tenderId;

    private UUID authorId;
}
