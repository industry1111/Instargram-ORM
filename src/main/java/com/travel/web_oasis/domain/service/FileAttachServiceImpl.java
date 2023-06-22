package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.repository.FileAttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileAttachServiceImpl implements FileAttachService {

    private final FileAttachRepository fileAttachRepository;

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
        String fileUrl = "http://localhost:8080/files/"; // 파일 URL 설정
        String fileName = multipartFile.getOriginalFilename();
        String fileContentType = multipartFile.getContentType();

        String fileStorageName = saveFileToStorage(multipartFile);

        FileAttach fileAttach = FileAttach.builder()
                .fileName(fileName)
                .fileStoreName(fileStorageName)
                .fileUrl(fileUrl + fileName)
                .FileType(fileContentType)
                .fileSize(multipartFile.getSize())

                .build();
        System.out.println("fileAttach = " + fileAttach);
        return fileAttachRepository.save(fileAttach);
    }

    private String saveFileToStorage(MultipartFile multipartFile) {
        String filePath = "/Users/gohyeong-gyu/Downloads/upload/" + multipartFile.getOriginalFilename(); // 파일 저장 경로 설정

        try {
            multipartFile.transferTo(new File(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }
}

