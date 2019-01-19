package nju.agile.travel.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import nju.agile.travel.util.DateUtil;
import nju.agile.travel.vo.OSSInfoVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by echo on 2019/1/17.
 */
@RestController
public class FileController {

    @Value("${oss.accessid}")
    String accessKeyId;

    @Value("${oss.access_secret}")
    String accessKeySecret;

    @Value("${oss.endpoint}")
    String endpoint;

    @Value("${oss.bucket}")
    String bucket;

//    @PostMapping("/upload/{dir}")
//    public String upload(@RequestParam("file") MultipartFile file, @PathVariable String dir){
//        System.out.println("????");
//        System.out.println(file);
//        String objectName = DateUtil.getCurrentMillsc() + file.getOriginalFilename();
//        String pathName = dir + "/" + objectName;
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//
//        try(BufferedOutputStream out = new BufferedOutputStream(
//                new FileOutputStream(new File(objectName)))
//        ){
//            out.write(file.getBytes());
//            out.flush();
//            System.out.println("objectName: "+objectName);
//            System.out.println("pathName: "+pathName);
//            ossClient.putObject(bucket, pathName, new File(objectName));
//
//            if(!new File(objectName).delete()){
//                throw new RuntimeException("上传文件未被正确删除!");
//            }
//
//        } catch (IOException ioException){
//            ioException.printStackTrace();
//        } finally {
//            ossClient.shutdown();
//        }
//
//        return String.format("%s.%s/%s",bucket, endpoint, pathName);
//
//    }

    @PostMapping("/upload/{dir}")
    public String upload(HttpServletRequest request, @PathVariable String dir) {
        StandardMultipartHttpServletRequest httpServletRequest = (StandardMultipartHttpServletRequest) request;
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        Iterator<String> iterator = httpServletRequest.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = httpServletRequest.getFile(iterator.next());
            String fileNames = file.getOriginalFilename();
            int split = fileNames.lastIndexOf(".");
//            System.out.println("fileNames = " + fileNames);
            String objectName = DateUtil.getCurrentMillsc() + fileNames;
            String pathName = dir + "/" + objectName;
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(objectName)))
            ) {
                out.write(file.getBytes());
                out.flush();
//                System.out.println("objectName: " + objectName);
//                System.out.println("pathName: " + pathName);
                ossClient.putObject(bucket, pathName, new File(objectName));

                if (!new File(objectName).delete()) {
                    throw new RuntimeException("上传文件未被正确删除!");
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                ossClient.shutdown();
            }

            return String.format("%s.%s/%s", bucket, endpoint, pathName);
        }
        return null;
    }

}
