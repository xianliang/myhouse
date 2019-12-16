package com.example.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-04 14:48
 */
@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Value("")
    private String filePath;
    public List<String> getImgPaths(List<MultipartFile> files){
        if(Strings.isNullOrEmpty(filePath)){
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        files.forEach(file ->{
            File localFile = null;
            if(!file.isEmpty()){
                try {
                    localFile = saveToLocal(file);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                }catch (Exception e){
                    logger.error("文件保存异常：",e);
                }
            }

        });
        return paths;
    }

    public static String getResourcePath(){
        File file = new File("D:\\xianlianggit\\myhousev1\\house-web\\src\\main\\resources\\static\\static\\imgs");
        String absulurePath = file.getAbsolutePath();
        return absulurePath;
    }

    private File saveToLocal(MultipartFile file)throws Exception{
        File newFile = new File(filePath+"/"+ Instant.now().getEpochSecond()+"/"+file.getOriginalFilename());
        logger.info("文件保存全路径"+newFile.getAbsolutePath());
        if(!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }

}
