package com.tendering_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая сотрудника (пользователя) системы.
 * Содержит основную информацию о сотруднике, включая уникальное имя пользователя.
 */
@Data
@Entity
@Table(name = "employee")
public class Employee {

    /**
     * Уникальный идентификатор сотрудника
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Имя пользователя
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Имя сотрудника
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия сотрудника
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Время создания записи о сотруднике
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Время последнего обновления записи о сотруднике
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
