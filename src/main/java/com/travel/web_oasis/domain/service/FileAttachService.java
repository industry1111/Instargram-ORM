package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.entity.FileAttach;
import com.travel.web_oasis.web.dto.FileAttachDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileAttachService {
    List<FileAttachDTO> upload(List<MultipartFile> multipartFiles);

    //    void deleteFiles(List<FileAttach> fileAttachList);
    void deleteFiles(String[] fileStoreNames);
    String getFullPath(String fileName);

    String getFileType(String fileName);
}
