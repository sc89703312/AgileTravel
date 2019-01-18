package nju.agile.travel.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import nju.agile.travel.vo.OSSInfoVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

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
    
}
