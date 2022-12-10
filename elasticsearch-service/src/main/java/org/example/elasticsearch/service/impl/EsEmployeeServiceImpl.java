package org.example.elasticsearch.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.elasticsearch.entity.Employee;
import org.example.elasticsearch.service.EsEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;

@Service
public class EsEmployeeServiceImpl implements EsEmployeeService {
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX_NAME = "megacorp";
    private static final String TYPE_NAME = "employee";
    @Autowired
    public EsEmployeeServiceImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public void addDocument() {
        IndexRequest request = new IndexRequest(INDEX_NAME, TYPE_NAME, "4");
        Employee employee = new Employee();
        employee.setFirst_name("codefarmer");
        employee.setLast_name("008");
        employee.setAge(20);
        employee.setAbout("i like football");
        employee.setInterests(Arrays.asList("football","eat"));
        // 将对象转换为 byte 数组
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] json = mapper.writeValueAsBytes(employee);
            // 设置文档内容
            request.source(json, XContentType.JSON);
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            System.out.println(response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getDocument() {
        GetRequest request = new GetRequest(INDEX_NAME, TYPE_NAME, "4");
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            if(response.isExists()){
                ObjectMapper mapper = new ObjectMapper();
                Employee employee = mapper.readValue(response.getSourceAsBytes(), Employee.class);
                System.out.println(employee);
                return employee;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDocument() {
        UpdateRequest request = new UpdateRequest(INDEX_NAME, TYPE_NAME, "4");
        try {
            // 设置文档内容
            request.doc("about", "i like basketball");
            UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            System.out.println(response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteDocument() {
        DeleteRequest request = new DeleteRequest(INDEX_NAME, TYPE_NAME, "4");
        try {
            DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            System.out.println(response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
