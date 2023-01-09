package com.fd.finema.bpcs;

import com.fd.finema.bom.Response;
import com.fd.finema.bom.ResponseEnum;
import com.fd.finema.services.FileService;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;

@RestController
@Api(tags = "HallController")
@CrossOrigin(origins = "*")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @POST
    @CrossOrigin
    @PostMapping(path = "/addFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file) throws FileUploadException {
        fileService.save(file);
        return new Response(ResponseEnum.SUCCESS,"OK",(short)200);
    }
}
