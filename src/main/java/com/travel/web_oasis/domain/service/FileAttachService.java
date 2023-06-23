package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.files.FileAttach;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileAttachService {
    List<FileAttach> upload(List<MultipartFile> multipartFiles);
    void deleteFiles(List<FileAttach> fileAttachList);
}
