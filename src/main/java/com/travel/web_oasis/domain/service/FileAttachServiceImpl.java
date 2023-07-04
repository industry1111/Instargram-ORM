package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import com.travel.web_oasis.domain.repository.FileAttachRepository;
import com.travel.web_oasis.web.dto.FileAttachDTO;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileAttachServiceImpl implements FileAttachService {

    @Autowired
    private FileAttachRepository fileAttachRepository;

    @Value("${file.storage.path}")
    private  String storagePath;
    private static final Logger logger = LoggerFactory.getLogger(FileAttachServiceImpl.class);

    @Override
    public List<FileAttachDTO> upload(List<MultipartFile> multipartFiles) {
        List<FileAttachDTO> fileAttachDTOList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            FileAttachDTO fileAttachDTO = uploadSingleFile(multipartFile);
            fileAttachDTOList.add(fileAttachDTO);
        }

        return fileAttachDTOList;
    }


    /* 단일 파일 업로드
    *Param
    * */
    private FileAttachDTO uploadSingleFile(MultipartFile multipartFile) {
        String fileUrl = "http://localhost:8080/files/"; // 파일 URL 설정 -> 환경변수로 설정 예정
        String fileName = multipartFile.getOriginalFilename();
        String fileContentType = multipartFile.getContentType();

        String fileStorageName = saveFileToStorage(multipartFile);
        logger.info("fileName : {} , fileContentType : {}", fileName, fileContentType);
        return FileAttachDTO.builder()
                .fileName(fileName)
                .fileStoreName(fileStorageName)
                .fileUrl(fileUrl + fileName)
                .fileType(fileContentType)
                .fileSize(multipartFile.getSize())
                .build();
    }


    /* 업로드한 파일을 저장소에 저장
    * Param
    *  multipartFile : 사용자가 화면상에서 업로드한 파일
    *
    * return : 저장시 UUID로 생성된 파일명
    * */
    private String saveFileToStorage(MultipartFile multipartFile) {

        String originalFilename = multipartFile.getOriginalFilename();

        String storeFileName = createStoreFilename(originalFilename);

        try {
            //저장경로 + 파일이름의 위치에 파일생성
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storeFileName;
    }

    /* UUID를 이용한 파일명 생성
    * Param
    *  originalFileName : 사용자가 실제 업로드한 파일명 (logo.png)
    *
    * return : UUID + '.' + 확장자*/
    private String createStoreFilename(String originalFilename) {

        //확장자
        String ext = extracted(originalFilename);

        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    /* 파일 확장자 추출
    * Param
    *  originalFileName : 사용자가 실제 업로드한 파일명 (logo.png)
    *
    * return : 파일 확장자 (png)
    * */
    private String extracted(String originalFileName) {
        int pos = originalFileName.lastIndexOf('.');
        return originalFileName.substring(pos + 1);
    }

    /*저장 경로
    * Param
    *  fileName : UUID.확장자가 붙은 파일명
    *
    * return : 저장경로
    * */
    @Override
    public String getFullPath(String fileName) {
        return storagePath + fileName;
    }

    @Override
    public void deleteFiles(List<FileAttach> fileAttachList) {
        for (FileAttach fileAttach : fileAttachList) {
            deleteFileFromStorage(fileAttach);
        }
    }

    private void deleteFileFromStorage(FileAttach fileAttach) {
        Path storePath = Paths.get(fileAttach.getFileStoreName());
        logger.info("deleteFileFromStorage() called : {}", storePath);
        //파일이 존재할 때만 삭제
        try {
            if (Files.exists(storePath)) {
                Files.delete(storePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileType(String fileName) {

        return fileAttachRepository.findByFileStoreName(fileName).getFileType();
    }

    public static List<FileAttach> dtosToEntities(List<FileAttachDTO> fileAttachDTOS) {
        List<FileAttach> fileAttachList = new ArrayList<>();
        if (fileAttachDTOS != null) {
            for (FileAttachDTO fileAttachDTO : fileAttachDTOS) {
                fileAttachList.add(dtoToEntity(fileAttachDTO));
            }
        }
        return fileAttachList;
    }

    private static FileAttach dtoToEntity(FileAttachDTO fileAttachDTO) {
        return FileAttach.builder()
                .fileName(fileAttachDTO.getFileName())
                .fileUrl(fileAttachDTO.getFileUrl())
                .fileStoreName(fileAttachDTO.getFileStoreName())
                .fileSize(fileAttachDTO.getFileSize())
                .fileType(fileAttachDTO.getFileType())
                .build();
    }

    public static List<FileAttachDTO> entitiesToDtos(List<FileAttach> fileAttachList) {
        List<FileAttachDTO> fileAttachDTOList = new ArrayList<>();

        for (FileAttach fileAttach : fileAttachList) {
            fileAttachDTOList.add(entityToDto(fileAttach));
        }

        return fileAttachDTOList;
    }

    private static FileAttachDTO entityToDto(FileAttach fileAttach) {
        return FileAttachDTO.builder()
                .fileName(fileAttach.getFileName())
                .fileUrl(fileAttach.getFileUrl())
                .fileStoreName(fileAttach.getFileStoreName())
                .fileSize(fileAttach.getFileSize())
                .fileType(fileAttach.getFileType())
                .build();
    }
}

