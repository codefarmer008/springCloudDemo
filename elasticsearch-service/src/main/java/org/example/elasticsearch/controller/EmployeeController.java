package org.example.elasticsearch.controller;

import org.example.elasticsearch.entity.Employee;
import org.example.elasticsearch.service.EsEmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EsEmployeeService esEmployeeService;

    @GetMapping("/es/query")
    public Employee queryEmployee() {
        return esEmployeeService.getDocument();
    }

    @GetMapping("/es/add")
    public String addEmployee() {
        esEmployeeService.addDocument();
        return "success";
    }

    @GetMapping("/es/update")
    public String updateEmployee() {
        esEmployeeService.updateDocument();
        return "success";
    }

    @GetMapping("/es/del")
    public String delEmployee() {
        esEmployeeService.deleteDocument();
        return "success";
    }
}
