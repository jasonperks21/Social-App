package com.cs334.project3.api;

import com.cs334.project3.dao.TestRepository;
import com.cs334.project3.model.Test;
import com.cs334.project3.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping("/test")
    public ResponseEntity<List<Test>> getAll(Model model){
        List<Test> data =  testService.getAll();
        return ResponseEntity.ok(data);
    }


}
