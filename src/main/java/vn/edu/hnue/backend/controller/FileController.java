package vn.edu.hnue.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.service.file.FileUpload;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {
    @Autowired
    private FileUpload fileUpload;
    @PostMapping("/upload")
    public ResponseEntity<ResponseBean> upload(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        return  new ResponseEntity<>(fileUpload.uploadFile(multipartFile), HttpStatus.OK);
    }
}
