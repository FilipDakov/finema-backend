package com.fd.finema.services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void save(MultipartFile file) throws FileUploadException;
}
