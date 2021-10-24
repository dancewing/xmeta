package io.xmeta.admin.controller;

import io.xmeta.admin.service.DatabaseMetaService;
import org.schemaspy.model.Database;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DatabaseController {
    private final DatabaseMetaService databaseMetaService;

    public DatabaseController(DatabaseMetaService databaseMetaService) {
        this.databaseMetaService = databaseMetaService;
    }

    @GetMapping("/database/{environmentId}")
    public ResponseEntity<Database> load(@PathVariable String environmentId) {
        return ResponseEntity.ok(this.databaseMetaService.load(environmentId));
    }
}
