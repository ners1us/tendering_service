package com.tendering_service.controllers;

import com.tendering_service.dto.TenderDto;
import com.tendering_service.enums.ServiceType;
import com.tendering_service.enums.TenderStatus;
import com.tendering_service.requests.TenderEditRequest;
import com.tendering_service.requests.TenderRequest;
import com.tendering_service.services.tender.TenderFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления тендерами.
 * Все запросы обрабатываются через /api/tenders.
 */
@RestController
@RequestMapping("/api/tenders")
@AllArgsConstructor
public class TenderController {

    private final TenderFacadeService tenderFacadeService;

    /**
     * Возвращает список всех тендеров.
     *
     * @return список тендеров в виде {@link List<TenderDto>}
     */
    @GetMapping
    public ResponseEntity<List<TenderDto>> getAllTenders() {
        List<TenderDto> tenders = tenderFacadeService.getAllTenders();

        return ResponseEntity.ok(tenders);
    }

    /**
     * Создает новый тендер.
     *
     * @param tenderRequest запрос, содержащий данные для нового тендера
     * @return созданный тендер в виде {@link TenderDto}
     */
    @PostMapping("/new")
    public ResponseEntity<TenderDto> createTender(@RequestBody TenderRequest tenderRequest) {
        TenderDto tenderDto = new TenderDto();

        tenderDto.setName(tenderRequest.getName());
        tenderDto.setDescription(tenderRequest.getDescription());
        tenderDto.setServiceType(ServiceType.valueOf(tenderRequest.getServiceType()));
        tenderDto.setOrganization(tenderRequest.getOrganizationId());
        tenderDto.setCreatorUsername(tenderRequest.getCreatorUsername());
        TenderDto createdTender = tenderFacadeService.createTender(tenderDto);

        return ResponseEntity.ok(createdTender);
    }

    /**
     * Откатывает тендер до указанной версии.
     *
     * @param tenderId идентификатор тендера
     * @param version  номер версии для отката
     * @param username имя пользователя
     * @return откатанный тендер в виде объекта {@link TenderDto}
     */
    @PutMapping("/{tenderId}/rollback/{version}")
    public ResponseEntity<TenderDto> rollbackTender(@PathVariable String tenderId,
                                                    @PathVariable Integer version,
                                                    @RequestParam String username) {
        TenderDto rolledBackTender = tenderFacadeService.rollbackTender(UUID.fromString(tenderId), version, username);

        return ResponseEntity.ok(rolledBackTender);
    }

    /**
     * Возвращает статус тендера.
     *
     * @param tenderId идентификатор тендера
     * @param username имя пользователя
     * @return строка, представляющая статус тендера
     */
    @GetMapping("/{tenderId}/status")
    public ResponseEntity<String> getTenderStatus(@PathVariable String tenderId,
                                                  @RequestParam String username) {
        String status = tenderFacadeService.getTenderStatus(UUID.fromString(tenderId), username);

        return ResponseEntity.ok(status);
    }

    /**
     * Обновляет статус тендера.
     *
     * @param tenderId идентификатор тендера
     * @param status   новый статус тендера
     * @param username имя пользователя
     * @return обновленный тендер в виде {@link TenderDto}
     */
    @PutMapping("/{tenderId}/status")
    public ResponseEntity<TenderDto> updateTenderStatus(@PathVariable String tenderId,
                                                        @RequestParam String status,
                                                        @RequestParam String username) {
        TenderDto updatedTender = tenderFacadeService.updateTenderStatus(UUID.fromString(tenderId), TenderStatus.valueOf(status.toUpperCase()), username);

        tenderFacadeService.incrementTenderVersion(UUID.fromString(tenderId));

        return ResponseEntity.ok(updatedTender);
    }

    /**
     * Возвращает список тендеров текущего пользователя.
     *
     * @param username имя пользователя
     * @return список тендеров пользователя в виде {@link List<TenderDto>}
     */
    @GetMapping("/my")
    public ResponseEntity<List<TenderDto>> getMyTenders(@RequestParam String username) {
        List<TenderDto> myTenders = tenderFacadeService.getTendersByUsername(username);

        return ResponseEntity.ok(myTenders);
    }

    /**
     * Редактирует указанный тендер.
     *
     * @param tenderId          идентификатор тендера
     * @param username          имя пользователя
     * @param tenderEditRequest запрос, содержащий данные для редактирования тендера
     * @return обновленный тендер в виде {@link TenderDto}
     */
    @PatchMapping("/{tenderId}/edit")
    public ResponseEntity<TenderDto> editTender(@PathVariable String tenderId,
                                                @RequestParam String username,
                                                @RequestBody TenderEditRequest tenderEditRequest) {
        TenderDto updatedTender = tenderFacadeService.editTender(UUID.fromString(tenderId), username, tenderEditRequest);

        tenderFacadeService.incrementTenderVersion(UUID.fromString(tenderId));

        return ResponseEntity.ok(updatedTender);
    }
}
