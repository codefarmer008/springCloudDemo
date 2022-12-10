package org.example.elasticsearch.service;

import org.example.elasticsearch.entity.Employee;

public interface EsEmployeeService {
    /**
     * 增加文档信息
     */
    public void addDocument();

    /**
     * 获取文档信息
     */
    public Employee getDocument();

    /**
     * 更新文档信息
     */
    public void updateDocument();

    /**
     * 删除文档信息
     */
    public void deleteDocument();
}
