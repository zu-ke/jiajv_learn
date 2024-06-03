package com.zuke.zukeliving.commodity.controller;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zuke.common.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Controller
public class TestController {

    // 方式一，编写方法，上传指定文件到bucket
    // AccessKey ID: LTAI5t6qi9P6AUtE14AaM8Jo
    // AccessKey Secret: BkRo83yD0j1OP2iyaF0C1WoO8Ya0xI
    @RequestMapping("/upload1")
    @ResponseBody
    public R upload1() throws FileNotFoundException, com.aliyuncs.exceptions.ClientException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-chengdu.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5t6qi9P6AUtE14AaM8Jo";
        String accessKeySecret = "BkRo83yD0j1OP2iyaF0C1WoO8Ya0xI";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "hsp-jiajv-100";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // 文件上传后的文件名
        String objectName = "touxiang.jpg";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        // 这里指定你要上传的文件完整的路径
        String filePath = "D:\\code\\jiajv_renrenfast_learn\\jiajv-commodity\\src\\main\\resources\\images\\touxiang.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return R.ok();
    }

    // 方式二，编写方法，上传指定文件到bucket

    @Resource
    private OSSClient ossClient;

    // AccessKey ID: LTAI5t6qi9P6AUtE14AaM8Jo
    // AccessKey Secret: BkRo83yD0j1OP2iyaF0C1WoO8Ya0xI
    @RequestMapping("/upload2")
    @ResponseBody
    public R upload2() throws Exception {
        String bucketName = "hsp-jiajv-100";
        InputStream inputStream = new FileInputStream("D:\\code\\jiajv_renrenfast_learn\\jiajv-commodity\\src\\main\\resources\\images\\1308322.jpeg");
        ossClient.putObject(bucketName, "1308322.jpeg", inputStream);
        ossClient.shutdown();
        return R.ok("上传完毕");
    }

    @RequestMapping("/t1")
    @ResponseBody
    public R t1() {
        int a = 10 / 0;
        return R.ok();
    }
}
