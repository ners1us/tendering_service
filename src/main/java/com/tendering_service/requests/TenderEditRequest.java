package com.tendering_service.requests;

import lombok.Data;

@Data
public class TenderEditRequest {
    private String name;

    private String description;

    private String serviceType;
}