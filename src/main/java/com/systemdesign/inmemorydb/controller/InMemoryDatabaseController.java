package com.systemdesign.inmemorydb.controller;

import com.systemdesign.inmemorydb.dao.entity.Node;
import com.systemdesign.inmemorydb.service.ConsistentHashingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "InMemoryDB", description = "In Memory DB APIs")
@RestController
@RequestMapping("api/v1")
public class InMemoryDatabaseController {
    private final ConsistentHashingService consistentHashingService;

    public InMemoryDatabaseController(ConsistentHashingService consistentHashingService) {
        this.consistentHashingService = consistentHashingService;
    }

    @Operation(summary = "Add new node")
    @PostMapping("/nodes")
    public ResponseEntity<String> addNode(@RequestParam String nodeId) {
        consistentHashingService.addNode(new Node(nodeId));
        return ResponseEntity.ok("Node added successfully.");
    }

    @Operation(summary = "Add a node")
    @DeleteMapping("/nodes/{nodeId}")
    public ResponseEntity<String> removeNode(@PathVariable String nodeId) {
        consistentHashingService.removeNode(nodeId);
        return ResponseEntity.ok("Node removed successfully.");
    }

    @Operation(summary = "Add data in DB")
    @PostMapping("/data")
    public ResponseEntity<String> addData(@RequestParam String key, @RequestParam String value) {
        consistentHashingService.putData(key, value);
        return ResponseEntity.ok("Data added successfully.");
    }

    @Operation(summary = "Get data from DB by Key")
    @GetMapping("/data/{key}")
    public ResponseEntity<String> getData(@PathVariable String key) {
        String value = consistentHashingService.getData(key);

        return (value != null)
                ? ResponseEntity.ok("Retrieved data: " + value)
                : ResponseEntity.status(404).body("Data not found.");
    }

    @Operation(summary = "Get all nodes details")
    @GetMapping("/nodes")
    public ResponseEntity<Map<String, Integer>> getNodeSummary() {
        Map<String, Integer> nodeSummary = consistentHashingService.getNodeSummary();
        return ResponseEntity.ok(nodeSummary);
    }
}
