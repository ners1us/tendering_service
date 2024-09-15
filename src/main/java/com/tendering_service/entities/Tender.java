package com.tendering_service.entities;

import com.tendering_service.enums.ServiceType;
import com.tendering_service.enums.TenderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Сущность, представляющая тендер в системе.
 * Хранит информацию о тендере, его статусе, типе услуги и авторе.
 */
@Entity
@Table(name = "tender")
@Data
public class Tender {

    /** Уникальный идентификатор тендера */
    @Id
    @GeneratedValue
    private UUID id;

    /** Название тендера */
    @Column(nullable = false)
    private String name;

    /** Описание тендера */
    @Column(nullable = false)
    private String description;

    /** Идентификатор организации, проводящей тендер */
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    /** Тип услуги, связанной с тендером */
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    /** Статус тендера */
    @Enumerated(EnumType.STRING)
    private TenderStatus status;

    /** Время создания тендера */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /** Время последнего обновления тендера */
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /** Версия тендера */
    @Column(nullable = false)
    private Integer version;

    /** Имя пользователя, создавшего тендер */
    @Column(nullable = false)
    private String creatorUsername;

    /**
     * Метод, вызываемый перед сохранением нового тендера.
     * Устанавливает временные метки создания и обновления.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Метод, вызываемый перед обновлением тендера.
     * Обновляет временную метку обновления.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
