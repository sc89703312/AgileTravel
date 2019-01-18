package nju.agile.travel.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.RequestSigner;
import com.aliyun.oss.common.comm.RequestMessage;

import com.aliyun.oss.model.PutObjectResult;
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

    @GetMapping("/oss/{dir}")
    public OSSInfoVO signature(@PathVariable String dir){
        String host = "http://" + bucket + "." + endpoint;
        dir += "/";

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = new byte[0];
        try {
            binaryData = postPolicy.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);

        return new OSSInfoVO(
                accessKeyId,
                encodedPolicy,
                postSignature,
                dir,
                host,
                String.valueOf(expireEndTime / 1000));
    }

    @PostMapping("/upload/{dir}")
    public String upload(@RequestParam("file") MultipartFile file, @PathVariable String dir) {

        String objectName = file.getOriginalFilename();
        String pathName = dir + "/" + objectName;

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(objectName)));
            out.write(file.getBytes());
            out.flush();
            out.close();

            ossClient.putObject(bucket, pathName, new File(objectName));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            new File(objectName).delete();
            ossClient.shutdown();
        }

        return String.format("%s.%s/%s", bucket, endpoint, pathName);

    }
}
