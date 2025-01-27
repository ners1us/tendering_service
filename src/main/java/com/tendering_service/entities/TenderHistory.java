package com.tendering_service.entities;

import com.tendering_service.enums.ServiceType;
import com.tendering_service.enums.TenderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая историю изменений тендера.
 * Хранит полные данные тендера в момент изменения.
 */
@Entity
@Table(name = "tender_history")
@Data
public class TenderHistory {

    /**
     * Уникальный идентификатор записи истории тендера
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Связанный тендер
     */
    @ManyToOne
    @JoinColumn(name = "tender_id", nullable = false)
    private Tender tender;

    /**
     * Название тендера на момент изменения
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание тендера на момент изменения
     */
    @Column(nullable = false)
    private String description;

    /**
     * Идентификатор организации на момент изменения
     */
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    /**
     * Тип услуги на момент изменения
     */
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    /**
     * Статус тендера на момент изменения
     */
    @Enumerated(EnumType.STRING)
    private TenderStatus status;

    /**
     * Время создания записи истории тендера
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Время последнего обновления записи истории тендера
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * Версия тендера на момент изменения
     */
    @Column(nullable = false)
    private Integer version;

    /**
     * Имя пользователя, создавшего тендер
     */
    @Column(nullable = false)
    private String creatorUsername;

    /**
     * Метод, вызываемый перед сохранением новой записи истории тендера.
     * Устанавливает временные метки создания и обновления.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Метод, вызываемый перед обновлением записи истории тендера.
     * Обновляет временную метку обновления.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
