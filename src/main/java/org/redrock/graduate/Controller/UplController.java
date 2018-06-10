package org.redrock.graduate.Controller;

import org.redrock.graduate.bean.Msg;
import org.redrock.graduate.util.Base64ToMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by momo on 2018/6/9
 */
@RestController
public class UplController {
    protected static final Logger logger = LoggerFactory.getLogger(UplController.class);

    @Value("${uplpath}")
    private String UPLOADED_FOLDER ;

    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }
    //上传接口
    @PostMapping("/u/upload") // //new annotation since 4.3
    public ResponseEntity<Msg> singleFileUpload(@RequestParam("b64f") String base64, @RequestParam(value="name",required = false,defaultValue="") String name) {
        Msg msg = new Msg();
        logger.info("开始将b64转换为MultipartFile");
        MultipartFile file = Base64ToMultipartFile.base64ToMultipart(base64);
        logger.info("检查file状态");
        if (file.isEmpty()) {
            logger.info("非/空文件流");
            msg.setMsg("no file");
            msg.setState(400);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(400));
        }
        String OriginalFilename=file.getOriginalFilename();
        logger.info("获取file名字并检查文件");
        String suffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".")).toLowerCase();
        Random rand = new Random();
        //重命名
        String filename = name.equals("") ?OriginalFilename.split(suffix)[0]+"_imgtemp_"+System.currentTimeMillis()*rand.nextInt(10)+1:name;
        String Newfilename=filename+suffix;
        if (fileTypes.contains(suffix)) {
            try {
                logger.info("尝试上传");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + Newfilename);
                Files.write(path, bytes);
            } catch (IOException e) {
                logger.info("上传失败");
                e.printStackTrace();
            }
            logger.info("上传成功");
            msg.setMsg("success");
            msg.setState(200);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(200));

        }else {
            logger.info("文件非图片格式,请检查文件后重新发送");
            msg.setMsg("some error");
            msg.setState(500);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(500));
        }
    }
}
