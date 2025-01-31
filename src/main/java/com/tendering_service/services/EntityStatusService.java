package com.tendering_service.services;

import java.util.UUID;

public interface EntityStatusService<D, S> {
    D updateStatus(UUID id, S status, String username);

    String getStatus(UUID id, String username);
}
