package io.xmeta.graphql.controller;

import io.xmeta.graphql.mix.WorkspaceDomain;
import io.xmeta.graphql.service.WorkspaceService;
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
