package com.spm1.configuration;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



// 声明配置类，放入Spring容器
@Configuration
// 指定配置文件位置
@PropertySource(value = {"classpath:application-aliyun-oss.yaml"})
// 指定配置文件中自定义属性前缀
@ConfigurationProperties(prefix = "aliyun")
@Data// lombok
@Accessors(chain = true)// 开启链式调用
public class AliyunOssConfig {
    private final String endPoint = "oss-cn-beijing.aliyuncs.com";// 地域节点
    private final String accessKeyId = "LTAI5t9gV2wVUH6dgXeMkM7X";
    private final String accessKeySecret = "fGuvAQzcJM5olM5Y5ioLemVeiTWYKV";
    private final String bucketName = "spm-pancake";// OSS的Bucket名称
    private final String urlPrefix = "http://spm-pancake.oss-cn-beijing.aliyuncs.com";// Bucket 域名
    private final String fileHost = "http://spm-pancake.oss-cn-beijing.aliyuncs.com/";// 目标文件夹

    // 将OSS 客户端交给Spring容器托管
    @Bean
    public OSS OSSClient() {
        return new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }
}
