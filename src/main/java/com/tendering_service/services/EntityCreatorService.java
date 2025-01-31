package com.tendering_service.services;

public interface EntityCreatorService<D> {
    D create(D dto);
}
