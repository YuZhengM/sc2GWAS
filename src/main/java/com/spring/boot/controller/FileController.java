package com.spring.boot.controller;


import com.spring.boot.service.FileService;
import com.spring.boot.util.factory.LogFactory;
import com.spring.boot.util.factory.log.Log;
import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Yu Zhengmin
 */
@RequestMapping("/file")
@CrossOrigin
@RestController
public class FileController {

    private static final Log log = LogFactory.getLog(FileController.class);

    private FileService fileService;

    public FileController() {
    }

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return file path
     * @throws IOException IO 异常
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String path = fileService.uploadFile(file);
        return ResultUtil.success("[uploadFile]: success", path);
    }

}
