package com.travel.web_oasis.domain.repository;

import com.travel.web_oasis.domain.entity.FileAttach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileAttachRepository extends JpaRepository<FileAttach, Long> {

    FileAttach findByFileStoreName(String fileName);


}
