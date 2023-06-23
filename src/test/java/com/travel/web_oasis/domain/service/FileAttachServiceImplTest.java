package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("파일 업로드")
    void upload() throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile file = new MockMultipartFile("file", "logo.png", "text/plain", new FileInputStream("/Users/gohyeong-gyu/Downloads/logo.png"));
        MultipartFile file2 = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        files.add(file);
        files.add(file2);

        List<FileAttach> saveFiles = fileAttachService.upload(files);
        FileAttach saveFile = saveFiles.get(0);
        System.out.println("saveFile = " + saveFile);
        Assertions.assertThat(new File(saveFile.getFileStoreName()).exists()).isTrue();
    }

    @Test
    @DisplayName("파일 삭제")
    void deleteFiles() throws IOException {
        //given
        List<MultipartFile> files = new ArrayList<>();
        String[] expected = {"test0.txt", "test1.txt", "test2.txt", "test3.txt", "test4.txt"};
        for (String str: expected) {
            MultipartFile file = new MockMultipartFile("file", str, "text/plain", str.getBytes());
            files.add(file);
        }
        List<FileAttach> saveFiles = fileAttachService.upload(files);

        //when
        fileAttachService.deleteFiles(saveFiles);

        //then
        for (FileAttach file : saveFiles) {
            Assertions.assertThat(new File(file.getFileStoreName()).exists()).isFalse();
        }

    }
}