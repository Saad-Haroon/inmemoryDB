package com.systemdesign.inmemorydb.dao;

import com.systemdesign.inmemorydb.dao.entity.Node;

import java.util.Map;

public interface NodeRepository {
    void addNode(Node node);

    void removeNode(String nodeId);

    Node getNodeById(String nodeId);

    Map<String, Node> getAllNodes();
}
