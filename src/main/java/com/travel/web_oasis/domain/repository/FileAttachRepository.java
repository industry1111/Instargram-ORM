package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.files.FileAttach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttachRepository extends JpaRepository<FileAttach, Long> {

    List<FileAttach> findAllByPostId(Long id);

    FileAttach findByFileStoreName(String fileName);
}
