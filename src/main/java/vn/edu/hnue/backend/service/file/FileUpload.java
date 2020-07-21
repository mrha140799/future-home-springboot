package vn.edu.hnue.backend.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.util.MessageUtil;

import java.io.*;
import java.util.Date;

@Component
public class FileUpload {
    @Value("${file.uploadDir}")
    private String UPLOAD_DIR;
    @Autowired
    private MessageUtil messageUtil;
    public ResponseBean uploadFile(MultipartFile multipartFile) throws IOException {
        try {
            String imageType = multipartFile.getContentType().split("/")[0];
            if (imageType == null || !imageType.equals("image"))
                return new ResponseErrorBean(messageUtil.getMessageDefaultEmpty("error.avatar.malformed"));
            String fileName = String.valueOf((new Date()).getTime());
            String path = UPLOAD_DIR + fileName +".jpg";
            saveFile(multipartFile.getInputStream(), path);
            return  new ResponseSuccessBean(fileName);
        } catch (FileNotFoundException e) {
            return new ResponseErrorBean(e.getMessage());
        }
    }

    private void saveFile(InputStream inputStream, String path) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
