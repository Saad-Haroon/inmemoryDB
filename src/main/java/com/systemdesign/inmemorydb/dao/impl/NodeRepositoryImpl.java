package com.systemdesign.inmemorydb.dao.impl;

import com.systemdesign.inmemorydb.dao.NodeRepository;
import com.systemdesign.inmemorydb.dao.entity.Node;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class NodeRepositoryImpl implements NodeRepository {
    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public void removeNode(String nodeId) {
        nodes.remove(nodeId);
    }

    public Node getNodeById(String nodeId) {
        return nodes.get(nodeId);
    }

    public Map<String, Node> getAllNodes() {
        return nodes;
    }
}
