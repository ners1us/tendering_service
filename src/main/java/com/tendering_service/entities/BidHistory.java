package com.tendering_service.entities;

import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая историю изменений заявки.
 * Хранит данные заявки в момент изменения.
 */
@Entity
@Table(name = "bid_history")
@Data
public class BidHistory {

    /**
     * Уникальный идентификатор записи истории
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Связанная заявка
     */
    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    /**
     * Название заявки на момент изменения
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание заявки на момент изменения
     */
    @Column(nullable = false)
    private String description;

    /**
     * Идентификатор организации на момент изменения
     */
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    /**
     * Тип автора заявки на момент изменения
     */
    @Enumerated(EnumType.STRING)
    private AuthorType authorType;

    /**
     * Статус заявки на момент изменения
     */
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    /**
     * Версия заявки на момент изменения
     */
    @Column(nullable = false)
    private Integer version;

    /**
     * Время создания записи истории
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Время последнего обновления записи
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * Идентификатор автора заявки на момент изменения
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
     * Метод, вызываемый перед сохранением новой записи истории.
     * Устанавливает временные метки создания и обновления.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Метод, вызываемый перед обновлением записи истории.
     * Обновляет временную метку обновления.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
