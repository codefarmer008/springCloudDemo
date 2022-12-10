package org.example.elasticsearch.constant;

public enum EsIndexName {
    GOODS("goods");

    private String name;

    private EsIndexName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
