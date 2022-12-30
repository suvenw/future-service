package com.suven.framework.generator.utils;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by 陈淦彬
 * @date 2022/9/14
 */
public class FindRelevancyBeanFileVisitor extends SimpleFileVisitor<Path> {
    /**
     * 文件前缀
     */
    Map<String, String> imitationBeanMap = new HashMap<>();
    List<String> imitationBeanList;
    String imitationBeanName;

    public FindRelevancyBeanFileVisitor(List<String> imitationBeanList, String imitationBeanName) {
        this.imitationBeanName = imitationBeanName;
        this.imitationBeanList = imitationBeanList;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileSourceName = file.getFileName().toString();
        if (imitationBeanList.contains(fileSourceName)) {
            imitationBeanMap.put(fileSourceName.replace(imitationBeanName, ""), file.getParent().toString());
        }
        return FileVisitResult.SKIP_SUBTREE;
    }

    public Map<String, String> getImitationBeanMap() {
        return imitationBeanMap;
    }
}
