package com.tendering_service.entities;

import com.tendering_service.enums.OrganizationType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая организацию в системе.
 * Хранит данные об организации, включая тип и описание.
 */
@Data
@Entity
@Table(name = "organization")
public class Organization {

    /**
     * Уникальный идентификатор организации
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Название организации
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание организации
     */
    @Column(nullable = false)
    private String description;

    /**
     * Тип организации
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrganizationType type;

    /**
     * Время создания записи об организации
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Время последнего обновления записи об организации
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
