package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.repository.FileAttachRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileAttachServiceImpl implements FileAttachService {

    Logger log = org.slf4j.LoggerFactory.getLogger(FileAttachServiceImpl.class);

    @Override
    public List<FileAttach> upload(List<MultipartFile> multipartFiles) {
        List<FileAttach> fileAttachments = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            FileAttach fileAttach = uploadSingleFile(multipartFile);
            fileAttachments.add(fileAttach);
        }

        return fileAttachments;
    }

    private FileAttach uploadSingleFile(MultipartFile multipartFile) {
        String fileUrl = "http://localhost:8080/files/"; // 파일 URL 설정 -> 환경변수로 설정 예정
        String fileName = multipartFile.getOriginalFilename();
        String fileContentType = multipartFile.getContentType();

        String fileStorageName = saveFileToStorage(multipartFile);

        return FileAttach.builder()
                .fileName(fileName)
                .fileStoreName(fileStorageName)
                .fileUrl(fileUrl + fileName)
                .FileType(fileContentType)
                .fileSize(multipartFile.getSize())
                .build();
    }

    private String saveFileToStorage(MultipartFile multipartFile) {
        String storagePath = "/Users/gohyeong-gyu/Downloads/upload/"; // 파일 저장 경로 설정 -> 환경 변수로 설정 예정
        String fileName = multipartFile.getOriginalFilename();

        File file = new File(storagePath + fileName);

        //파일 이름이 존재하는지 확인
        if (file.exists()) {
            fileName += "_" + System.currentTimeMillis();
        }
        try {
            //저장경로 + 파일이름의 위치에 파일생성
            multipartFile.transferTo(new File(storagePath+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storagePath+fileName;
    }

    @Override
    public void deleteFiles(List<FileAttach> fileAttachList) {
        for (FileAttach fileAttach : fileAttachList) {
            deleteFileFromStorage(fileAttach);
        }
    }

    private void deleteFileFromStorage(FileAttach fileAttach) {
        Path storePath = Paths.get(fileAttach.getFileStoreName());
        log.info("deleteFileFromStorage() called : {}", storePath);
        //파일이 존재할 때만 삭제
        try {
            if (Files.exists(storePath)) {
                Files.delete(storePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

