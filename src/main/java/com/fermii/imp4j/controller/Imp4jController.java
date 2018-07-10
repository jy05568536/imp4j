package com.fermii.imp4j.controller;

import com.fermii.imp4j.service.Imp4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController(value = "imp4j")
@RequestMapping(value = "/imp4j")
public class Imp4jController {

    @Autowired
    private Imp4jService imp4jService;

    @PostMapping("/src/main/data/{tableName}")
    public Object getChildren(@PathVariable(value = "tableName") String tableName, @RequestParam(value = "file") MultipartFile file) throws Exception {
        return imp4jService.impData(tableName, file);
    }
}
