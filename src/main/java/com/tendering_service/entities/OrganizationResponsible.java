package com.tendering_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Сущность, представляющая связь между организацией и ответственным сотрудником.
 * Указывает, какой сотрудник отвечает за определённую организацию.
 */
@Data
@Entity
@Table(name = "organization_responsible")
public class OrganizationResponsible {

    /**
     * Уникальный идентификатор записи
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Связанная организация
     */
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    /**
     * Ответственный сотрудник
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Employee user;
}
