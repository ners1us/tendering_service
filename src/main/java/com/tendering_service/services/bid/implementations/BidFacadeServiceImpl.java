package com.tendering_service.services.bid.implementations;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import com.tendering_service.requests.BidEditRequest;
import com.tendering_service.services.bid.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления заявками (Bids)
 */
@Service
@AllArgsConstructor
public class BidFacadeServiceImpl implements BidFacadeService {

    private final BidDatabaseService bidDatabaseService;

    private final BidCreatorService bidCreatorService;

    private final BidStatusManagerService bidStatusManagerService;

    private final BidDataManagerService bidDataManagerService;

    private final BidFeedbackManagerService bidFeedbackManagerService;

    private final BidDecisionManagerService bidDecisionManagerService;

    /**
     * Создает новую заявку.
     *
     * @param bidDto информация о заявке
     * @return DTO созданной заявки
     */
    public BidDto createBid(BidDto bidDto) {
        return bidCreatorService.createBid(bidDto);
    }

    /**
     * Обновляет статус заявки.
     *
     * @param bidId идентификатор заявки
     * @param status новый статус заявки
     * @param username имя пользователя, выполняющего обновление
     * @return обновленная заявка
     */
    public BidDto updateBidStatus(UUID bidId, BidStatus status, String username) {
        return bidStatusManagerService.updateBidStatus(bidId, status, username);
    }

    /**
     * Получает статус заявки.
     *
     * @param bidId идентификатор заявки
     * @param username имя пользователя
     * @return строковое представление статуса заявки
     */
    public String getBidStatus(UUID bidId, String username) {
        return bidStatusManagerService.getBidStatus(bidId, username);
    }

    /**
     * Получает список заявок по тендеру.
     *
     * @param tenderId идентификатор тендера
     * @param username имя пользователя
     * @return список заявок по тендеру
     */
    public List<BidDto> getBidsForTender(UUID tenderId, String username) {
        return bidDatabaseService.getBidsForTender(tenderId, username);
    }

    /**
     * Откат заявки к предыдущей версии.
     *
     * @param bidId идентификатор заявки
     * @param targetVersion версия для отката
     * @param username имя пользователя
     * @return откатанная заявка
     */
    public BidDto rollbackBid(UUID bidId, Integer targetVersion, String username) {
        return bidDataManagerService.rollbackBid(bidId, targetVersion, username);
    }

    /**
     * Получает список заявок для пользователя по username.
     *
     * @param username имя пользователя
     * @return список заявок
     */
    public List<BidDto> getBidsByUsername(String username) {
        return bidDatabaseService.getBidsByUsername(username);
    }

    /**
     * Редактирует заявку.
     *
     * @param bidId идентификатор заявки
     * @param username имя пользователя, выполняющего редактирование
     * @param bidEditRequest запрос с изменениями для заявки
     * @return отредактированная заявка
     */
    public BidDto editBid(UUID bidId, String username, BidEditRequest bidEditRequest) {
        return bidDataManagerService.editBid(bidId, username, bidEditRequest);
    }

    /**
     * Увеличивает версию заявки.
     *
     * @param bidId идентификатор заявки
     */
    public void incrementBidVersion(UUID bidId) {
        bidDataManagerService.incrementBidVersion(bidId);
    }

    /**
     * Отправляет решение по заявке.
     *
     * @param bidId идентификатор заявки
     * @param status решение по заявке, представленное в виде {@link DecisionStatus}
     * @param username имя пользователя, отправляющего решение
     */
    public void submitDecision(UUID bidId, DecisionStatus status, String username) {
        bidDecisionManagerService.submitDecision(bidId, status, username);
    }

    /**
     * Отправляет отзыв на заявку.
     *
     * @param bidId идентификатор заявки
     * @param feedback текст отзыва на заявку
     * @param username имя пользователя, отправляющего отзыв
     */
    public void submitFeedback(UUID bidId, String feedback, String username) {
        bidFeedbackManagerService.submitFeedback(bidId, feedback, username);
    }
}
