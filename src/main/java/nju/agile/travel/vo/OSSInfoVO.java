package nju.agile.travel.vo;

import lombok.Data;

/**
 * Created by echo on 2019/1/17.
 */
@Data
public class OSSInfoVO {

    String accessId;
    String policy;
    String signature;
    String dir;
    String host;
    String expire;

    public OSSInfoVO(){}

    public OSSInfoVO(String accessId,
            String policy,
            String signature,
            String dir,
            String host,
            String expire){

        this.accessId = accessId;
        this.policy = policy;
        this.signature = signature;
        this.dir = dir;
        this.host = host;
        this.expire = expire;

    }

}
