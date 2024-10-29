package com.systemdesign.inmemorydb.service.impl;

import com.systemdesign.inmemorydb.dao.NodeRepository;
import com.systemdesign.inmemorydb.dao.entity.Data;
import com.systemdesign.inmemorydb.dao.entity.Node;
import com.systemdesign.inmemorydb.service.ConsistentHashingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class ConsistentHashingServiceImpl implements ConsistentHashingService {
    private final NodeRepository nodeRepository;
    private final SortedMap<Integer, Node> ring = new TreeMap<>();

    private static final int NUMBER_OF_REPLICAS = 3;

    public ConsistentHashingServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
        nodeRepository.getAllNodes().values().forEach(this::addNode);
    }

    public int hash(String key) {
        return key.hashCode() & 0x7fffffff;
    }

    public void addNode(Node node) {
        for (int i = 0; i < NUMBER_OF_REPLICAS; i++) {
            int hash = hash(node.getId() + i);
            ring.put(hash, node);
        }
        nodeRepository.addNode(node);
    }

    public void removeNode(String nodeId) {
        Node node = nodeRepository.getNodeById(nodeId);
        if (node != null) {
            for (int i = 0; i < NUMBER_OF_REPLICAS; i++) {
                int hash = hash(node.getId() + i);
                ring.remove(hash);
            }
            nodeRepository.removeNode(nodeId);
        }
    }

    public Node getNodeForKey(String key) {
        if (ring.isEmpty()) return null;
        int hash = hash(key);
        if (!ring.containsKey(hash)) {
            SortedMap<Integer, Node> tailMap = ring.tailMap(hash);
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }
        return ring.get(hash);
    }

    public void putData(String key, String value) {
        Node node = getNodeForKey(key);
        if (node != null) {
            node.addData(new Data(key, value));
        }
    }

    public String getData(String key) {
        Node node = getNodeForKey(key);
        Data entry = (node != null) ? node.getData(key) : null;
        return (entry != null) ? entry.getValue() : null;
    }

    public Map<String, Integer> getNodeSummary() {
        Map<String, Integer> summary = new HashMap<>();
        nodeRepository.getAllNodes().values().forEach(node -> {
            summary.put(node.getId(), node.getDataEntries().size());
        });
        return summary;
    }
}
