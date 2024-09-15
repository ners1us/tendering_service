package com.tendering_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для проверки состояния API.
 * Все запросы обрабатываются через /api/ping.
 */
@RestController
@RequestMapping("/api")
public class PingController {

    /**
     * Возвращает строку "ok" для подтверждения работы API.
     *
     * @return строка "ok" с кодом состояния 200 OK
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
