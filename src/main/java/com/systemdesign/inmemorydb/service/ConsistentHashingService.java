package com.systemdesign.inmemorydb.service;

import com.systemdesign.inmemorydb.dao.entity.Node;

import java.util.Map;

public interface ConsistentHashingService {
    int hash(String key);
    void addNode(Node node);
    void removeNode(String nodeId);
    Node getNodeForKey(String key);
    void putData(String key, String value);
    String getData(String key);
    Map<String, Integer> getNodeSummary();
}
