package nju.agile.travel.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import nju.agile.travel.util.DateUtil;
import nju.agile.travel.vo.OSSInfoVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
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

    @PostMapping("/upload/{dir}")
    public String upload(@RequestParam("file") MultipartFile file, @PathVariable String dir){
        String objectName = DateUtil.getCurrentMillsc() + file.getOriginalFilename();
        String pathName = dir + "/" + objectName;
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try(BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(new File(objectName)))
        ){
            out.write(file.getBytes());
            out.flush();

            ossClient.putObject(bucket, pathName, new File(objectName));

            if(!new File(objectName).delete()){
                throw new RuntimeException("上传文件未被正确删除!");
            }

        } catch (IOException ioException){
            ioException.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return String.format("%s.%s/%s",bucket, endpoint, pathName);

    }

}
