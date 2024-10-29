package com.systemdesign.inmemorydb.dao.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    private String key;
    private String value;

    public Data(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
