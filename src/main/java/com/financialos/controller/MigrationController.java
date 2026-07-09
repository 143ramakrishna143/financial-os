package com.financialos.controller;

import com.financialos.service.MigrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class MigrationController {

    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping("/migrate-legacy")
    public ResponseEntity<String> migrate() {
        String result = migrationService.migrateLegacyData();
        return ResponseEntity.ok(result);
    }
}

