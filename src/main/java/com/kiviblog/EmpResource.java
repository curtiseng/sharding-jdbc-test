package com.kiviblog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzifeng
 */
@RestController
@RequestMapping("/api")
public class EmpResource {

    private EmpRepository empRepository;

    public EmpResource(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @GetMapping("/emp")
    public ResponseEntity<Emp> getEmp() {
        return new ResponseEntity<>(empRepository.findOne("1"), HttpStatus.OK);
    }

    @PostMapping("/emp")
    public ResponseEntity<Emp> saveEmp() {
        Emp emp = new Emp();
        emp.setId("110");
        emp.setName("master");
        emp.setAge(11);
        return new ResponseEntity<>(empRepository.save(emp), HttpStatus.OK);
    }
}
