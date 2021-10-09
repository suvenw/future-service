package com.suven.framework.generator.service;

import org.springframework.stereotype.Service;
import com.suven.framework.generator.config.ProjectPathConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WriteGeneratorService {

    private LinkedHashMap<Integer, ProjectPathConfig> pathMap = new LinkedHashMap<>();

    public boolean put(Integer key, ProjectPathConfig value){
        pathMap.put(key,value);
        return true;
    }
    public boolean putIfAbsent(Integer key, ProjectPathConfig value){
        pathMap.putIfAbsent(key,value);
        return true;
    }

    public List<ProjectPathConfig> getList(){
        return new ArrayList<>(pathMap.values());
    }
}
