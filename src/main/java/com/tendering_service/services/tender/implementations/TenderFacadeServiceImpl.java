package com.tendering_service.services.tender.implementations;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.requests.TenderEditRequest;
import com.tendering_service.services.tender.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления тендерами (Tenders)
 */
@Service
@AllArgsConstructor
public class TenderFacadeServiceImpl implements TenderFacadeService {

    private final TenderDatabaseService tenderDatabaseService;

    private final TenderCreatorServiceImpl tenderCreatorServiceImpl;

    private final TenderStatusManagerService tenderStatusManagerService;

    private final TenderDataManagerService tenderDataManagerService;

    /**
     * Создает новый тендер.
     *
     * @param tenderDto информация о тендере
     * @return DTO созданного тендера
     */
    public TenderDto createTender(TenderDto tenderDto) {
        return tenderCreatorServiceImpl.createTender(tenderDto);
    }

    /**
     * Откатывает тендер к предыдущей версии.
     *
     * @param tenderId идентификатор тендера
     * @param targetVersion версия для отката
     * @param username имя пользователя, выполняющего откат
     * @return DTO откатанного тендера
     */
    public TenderDto rollbackTender(UUID tenderId, Integer targetVersion, String username) {
        return tenderDataManagerService.rollbackTender(tenderId, targetVersion, username);
    }

    /**
     * Обновляет статус тендера.
     *
     * @param tenderId идентификатор тендера
     * @param status новый статус тендера
     * @param username имя пользователя, выполняющего обновление
     * @return обновленный тендер
     */
    public TenderDto updateTenderStatus(UUID tenderId, TenderStatus status, String username) {
        return tenderStatusManagerService.updateTenderStatus(tenderId, status, username);
    }

    /**
     * Получает статус тендера.
     *
     * @param tenderId идентификатор тендера
     * @param username имя пользователя
     * @return строковое представление статуса тендера
     */
    public String getTenderStatus(UUID tenderId, String username) {
        return tenderStatusManagerService.getTenderStatus(tenderId, username);
    }

    /**
     * Увеличивает версию тендера.
     *
     * @param tenderId идентификатор тендера
     */
    public void incrementTenderVersion(UUID tenderId) {
        tenderDataManagerService.incrementTenderVersion(tenderId);
    }

    /**
     * Получает список тендеров по имени пользователя.
     *
     * @param username имя пользователя
     * @return список тендеров, созданных данным пользователем
     */
    public List<TenderDto> getTendersByUsername(String username) {
        return tenderDatabaseService.getTendersByUsername(username);
    }

    /**
     * Получает список всех тендеров.
     *
     * @return список всех тендеров
     */
    public List<TenderDto> getAllTenders() {
        return tenderDatabaseService.getAllTenders();
    }

    /**
     * Редактирует тендер.
     *
     * @param tenderId идентификатор тендера
     * @param username имя пользователя, выполняющего редактирование
     * @param tenderEditRequest запрос с изменениями для тендера
     * @return DTO отредактированного тендера
     */
    public TenderDto editTender(UUID tenderId, String username, TenderEditRequest tenderEditRequest) {
        return tenderDataManagerService.editTender(tenderId, username, tenderEditRequest);
    }
}
