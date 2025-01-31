package com.tendering_service.services;

import java.util.List;

public interface EntityDatabaseService<D> {
    List<D> getItemsByUsername(String username);

    List<D> getAllItems();
}
