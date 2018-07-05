package com.fermii.imp4j.service;

import com.fermii.imp4j.mapper.Imp4jMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Imp4jService {

    @Autowired
    private Imp4jMapper imp4jMapper;

    public int impData(String tableName, MultipartFile file) {
        return 0;
    }
}
