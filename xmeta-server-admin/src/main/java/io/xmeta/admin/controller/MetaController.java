package io.xmeta.admin.controller;

import io.xmeta.admin.mix.WorkspaceDomain;
import io.xmeta.admin.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MetaController {
    private final WorkspaceService workspaceService;

    public MetaController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<WorkspaceDomain>> metadata() {
        return ResponseEntity.ok(workspaceService.metadata());
    }
}
