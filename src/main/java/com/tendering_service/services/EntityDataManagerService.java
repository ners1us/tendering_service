package com.tendering_service.services;

import java.util.UUID;

public interface EntityDataManagerService<D, R> {
    D rollback(UUID id, Integer targetVersion, String username);

    void incrementVersion(UUID id);

    D edit(UUID id, String username, R editRequest);
}
