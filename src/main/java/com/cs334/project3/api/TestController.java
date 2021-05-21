package com.cs334.project3.api;

import com.cs334.project3.model.Test;
import com.cs334.project3.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping("/test")
    public ResponseEntity<List<Test>> getAll(){
        List<Test> data =  testService.getAll();
        return ResponseEntity.ok(data);
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public String add(@RequestBody Test t){
        testService.insert(t);
        return "THANKS FOR POSTING " + t.text;
    }


}
