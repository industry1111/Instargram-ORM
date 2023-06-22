package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileAttachServiceImplTest {

    @Autowired
    private FileAttachService fileAttachService;

    @Test
    void upload() throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile file = new MockMultipartFile("file", "logo.png", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png"));
        files.add(file);

        List<FileAttach> saveFiles = fileAttachService.upload(files);
        FileAttach saveFile = saveFiles.get(0);
        System.out.println("saveFile = " + saveFile);
        Assertions.assertThat(new File(saveFile.getFileStoreName()).exists()).isTrue();
    }
}