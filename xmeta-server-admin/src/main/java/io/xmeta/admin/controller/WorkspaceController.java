package io.xmeta.admin.controller;

import io.xmeta.admin.model.*;
import io.xmeta.admin.service.WorkspaceService;
import io.xmeta.core.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @DeleteMapping("/{id}")
    public Workspace deleteWorkspace(@PathVariable String id) {
        return null;
    }

    @PatchMapping("/{id}")
    public Workspace updateWorkspace(@RequestBody WorkspaceUpdateInput data, @PathVariable String id) {
        return this.workspaceService.updateWorkspace(data, id);
    }

    @PostMapping("")
    public R<Workspace> createWorkspace(@RequestBody WorkspaceCreateInput data) {
        return R.data(this.workspaceService.createWorkspace(data));
    }

    @GetMapping("/")
    public R<List<Workspace>> workspaces() {
        return R.data(this.workspaceService.getCurrentAccountWorkspaces());
    }

    @GetMapping("/{id}")
    public R<Workspace> workspace(@PathVariable String id) {
        return R.data(this.workspaceService.getWorkspace(id));
    }

    @GetMapping("/current")
    public R<Workspace> currentWorkspace() {
        return R.data(this.workspaceService.currentWorkspace());
    }
}
