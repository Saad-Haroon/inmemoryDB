package com.systemdesign.inmemorydb.dao.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Node {
    private final String id;
    private final Map<String, Data> dataEntries;

    public Node(String id) {
        this.id = id;
        this.dataEntries = new HashMap<>();
    }

    public void addData(Data data) {
        dataEntries.put(data.getKey(), data);
    }

    public Data getData(String key) {
        return dataEntries.get(key);
    }

    public void removeData(String key) {
        dataEntries.remove(key);
    }
}
