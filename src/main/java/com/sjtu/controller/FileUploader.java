package com.sjtu.controller;

/**
 * Created by xiaoke on 17-11-16.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileUploader {


    @ResponseBody
    @RequestMapping("/upload")
    public String  fileUpload2(HttpSession hs, @RequestParam("file") CommonsMultipartFile file) throws IOException {
        long  startTime=System.currentTimeMillis();
        String basePath = hs.getServletContext().getRealPath("/") + "statics/images/user";
        String oldFileType = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + oldFileType;
        File newFile=new File(basePath + "/" + newFileName);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long  endTime=System.currentTimeMillis();
        System.out.println("方法二的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return newFileName;
    }
}
