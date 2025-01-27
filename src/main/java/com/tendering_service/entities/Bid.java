package com.tendering_service.entities;

import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая заявку в системе тендеров.
 * Заявка связана с тендером и содержит информацию об авторе заявки,
 * статусе, а также временные метки создания и обновления.
 */
@Entity
@Table(name = "bid")
@Data
public class Bid {

    /**
     * Уникальный идентификатор заявки
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Название заявки
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание заявки
     */
    @Column(nullable = false)
    private String description;

    /**
     * Идентификатор тендера, к которому относится заявка
     */
    @Column(name = "tender_id", nullable = false)
    private UUID tenderId;

    /**
     * Идентификатор организации, сделавшей заявку
     */
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    /**
     * Тип автора заявки (пользователь или организация)
     */
    @Enumerated(EnumType.STRING)
    private AuthorType authorType;

    /**
     * Статус заявки
     */
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    /**
     * Версия заявки
     */
    @Column(nullable = false)
    private Integer version;

    /**
     * Время создания заявки
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Время обновления заявки
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * Идентификатор автора заявки
     */
    @Column(nullable = false)
    private UUID authorId;

    /**
     * Решение по предложению
     */
    @Column
    private DecisionStatus decision;

    /**
     * Отзыв по предложению
     */
    @Column
    private String feedback;

    /**
     * Метод, вызываемый перед сохранением новой записи в базу данных.
     * Устанавливает временные метки создания и обновления.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Метод, вызываемый перед обновлением записи в базе данных.
     * Обновляет временную метку обновления.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
