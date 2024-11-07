package com.tendering_service.controllers;

import com.tendering_service.dto.BidDto;
import com.tendering_service.enums.AuthorType;
import com.tendering_service.enums.BidStatus;
import com.tendering_service.enums.DecisionStatus;
import com.tendering_service.requests.BidEditRequest;
import com.tendering_service.requests.BidRequest;
import com.tendering_service.services.bid.BidFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления заявками.
 * Все запросы обрабатываются через /api/bids.
 */
@RestController
@RequestMapping("/api/bids")
@AllArgsConstructor
public class BidController {

    private final BidFacadeService bidFacadeService;

    /**
     * Создает новую заявку.
     *
     * @param bidRequest запрос, содержащий данные для создания заявки
     * @return созданная заявка в виде {@link BidDto}
     */
    @PostMapping("/new")
    public ResponseEntity<BidDto> createBid(@RequestBody BidRequest bidRequest) {
        BidDto bidDto = new BidDto();

        bidDto.setName(bidRequest.getName());
        bidDto.setDescription(bidRequest.getDescription());
        bidDto.setAuthorType(AuthorType.valueOf(bidRequest.getAuthorType()));
        bidDto.setTender(bidRequest.getTenderId());
        bidDto.setAuthorId(bidRequest.getAuthorId());
        BidDto createdBid = bidFacadeService.createBid(bidDto);

        return ResponseEntity.ok(createdBid);
    }

    /**
     * Возвращает список заявок для конкретного тендера.
     *
     * @param tenderId идентификатор тендера
     * @param username имя пользователя
     * @return список заявок в виде {@link List<BidDto>}
     */
    @GetMapping("/{tenderId}/list")
    public ResponseEntity<List<BidDto>> getBidsForTender(@PathVariable String tenderId,
                                                         @RequestParam String username) {
        List<BidDto> bids = bidFacadeService.getBidsForTender(UUID.fromString(tenderId), username);

        return ResponseEntity.ok(bids);
    }

    /**
     * Откатывает заявку до указанной версии.
     *
     * @param bidId идентификатор заявки
     * @param version номер версии для отката
     * @param username имя пользователя
     * @return откатанная заявка в виде {@link BidDto}
     */
    @PutMapping("/{bidId}/rollback/{version}")
    public ResponseEntity<BidDto> rollbackBid(@PathVariable String bidId,
                                              @PathVariable Integer version,
                                              @RequestParam String username) {
        BidDto rolledBackBid = bidFacadeService.rollbackBid(UUID.fromString(bidId), version, username);

        return ResponseEntity.ok(rolledBackBid);
    }

    /**
     * Возвращает статус заявки.
     *
     * @param bidId идентификатор заявки
     * @param username имя пользователя
     * @return строка, представляющая статус заявки
     */
    @GetMapping("/{bidId}/status")
    public ResponseEntity<String> getBidStatus(@PathVariable String bidId,
                                                  @RequestParam String username) {
        String status = bidFacadeService.getBidStatus(UUID.fromString(bidId), username);

        return ResponseEntity.ok(status);
    }

    /**
     * Обновляет статус заявки.
     *
     * @param bidId идентификатор заявки
     * @param status новый статус заявки
     * @param username имя пользователя
     * @return обновленная заявка в виде {@link BidDto}
     */
    @PutMapping("/{bidId}/status")
    public ResponseEntity<BidDto> updateBidStatus(@PathVariable String bidId,
                                                     @RequestParam String status,
                                                     @RequestParam String username) {
        BidDto updatedBid = bidFacadeService.updateBidStatus(UUID.fromString(bidId), BidStatus.valueOf(status.toUpperCase()), username);

        bidFacadeService.incrementBidVersion(UUID.fromString(bidId));

        return ResponseEntity.ok(updatedBid);
    }

    /**
     * Возвращает список заявок текущего пользователя.
     *
     * @param username имя пользователя
     * @return список заявок пользователя в виде {@link List<BidDto>}
     */
    @GetMapping("/my")
    public ResponseEntity<List<BidDto>> getMyBids(@RequestParam String username) {
        List<BidDto> myBids = bidFacadeService.getBidsByUsername(username);

        return ResponseEntity.ok(myBids);
    }

    /**
     * Обновляет заявку по указанному идентификатору.
     *
     * @param bidId идентификатор заявки, которую необходимо обновить
     * @param username имя пользователя, вносящего изменения в заявку
     * @param bidEditRequest объект, содержащий новые данные для заявки
     * @return обновленная заявка в виде {@link BidDto}
     */
    @PatchMapping("/{bidId}/edit")
    public ResponseEntity<BidDto> editBid(
            @PathVariable String bidId,
            @RequestParam String username,
            @RequestBody BidEditRequest bidEditRequest) {

        BidDto updatedBid = bidFacadeService.editBid(UUID.fromString(bidId), username, bidEditRequest);

        bidFacadeService.incrementBidVersion(UUID.fromString(bidId));

        return ResponseEntity.ok(updatedBid);
    }

    /**
     * Отправляет решение по заявке.
     *
     * @param bidId идентификатор заявки
     * @param decision решение, принятое по заявке, представленное в виде {@link DecisionStatus}
     * @param username имя пользователя, отправляющего решение
     * @return пустой ответ с кодом 200 OK при успешной отправке решения
     */
    @PutMapping("/{bidId}/submit_decision")
    public ResponseEntity<Void> submitDecision(
            @PathVariable String bidId,
            @RequestParam String decision,
            @RequestParam String username) {

        bidFacadeService.submitDecision(UUID.fromString(bidId), DecisionStatus.valueOf(decision), username);

        return ResponseEntity.ok().build();
    }

    /**
     * Отправляет отзыв на заявку.
     *
     * @param bidId идентификатор заявки
     * @param bidFeedback текст отзыва на заявку
     * @param username имя пользователя, отправляющего отзыв
     * @return пустой ответ с кодом 200 OK при успешной отправке отзыва
     */
    @PutMapping("/{bidId}/feedback")
    public ResponseEntity<Void> submitFeedback(
            @PathVariable String bidId,
            @RequestParam String bidFeedback,
            @RequestParam String username) {

        bidFacadeService.submitFeedback(UUID.fromString(bidId), bidFeedback, username);

        return ResponseEntity.ok().build();
    }
}
