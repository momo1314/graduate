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
        logger.info("b64 to MultipartFile");
        MultipartFile file = Base64ToMultipartFile.base64ToMultipart(base64);
        logger.info("check file");
        if (file.isEmpty()) {
            logger.info("empty file");
            msg.setMsg("no file");
            msg.setState(400);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(400));
        }
        String OriginalFilename=file.getOriginalFilename();
        logger.info("get file name");
        String suffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".")).toLowerCase();
        Random rand = new Random();
        //重命名
        String filename = name.equals("") ?OriginalFilename.split(suffix)[0]+"_imgtemp_"+System.currentTimeMillis()*rand.nextInt(10)+1:name;
        String Newfilename=filename+suffix;
        if (fileTypes.contains(suffix)) {
            try {
                logger.info("uploading");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + Newfilename);
                Files.write(path, bytes);
            } catch (IOException e) {
                logger.info("upload fail");
                e.printStackTrace();
            }
            logger.info("upload success");
            msg.setMsg("success");
            msg.setState(200);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(200));

        }else {
            logger.info("error suffix");
            msg.setMsg("some error");
            msg.setState(500);
            return new ResponseEntity<>(msg, HttpStatus.valueOf(500));
        }
    }
}
