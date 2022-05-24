package com.kubees.admin.attach;

import com.kubees.admin.attach.form.AttachFeedForm;
import com.kubees.admin.attach.form.AttachImg;
import com.kubees.domain.Attach;
import com.kubees.domain.AttachFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AttachFeedController {
    private final AttachFeedService attachFeedService;

    @GetMapping("/attach/{attachFeedOid}")
    public ResponseEntity<Resource> attachFeed(@PathVariable Long attachFeedOid) throws IOException {
        String filePath = "";


        AttachFeedForm attachFeedForm = attachFeedService.getAttachFeedProcessor(attachFeedOid);
        if (attachFeedForm.getSavedPath() != null && attachFeedForm.getThumbFileName() != null) {
            filePath = attachFeedForm.getSavedPath() + File.separator + attachFeedForm.getThumbFileName();
        }

        File file = new File(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(attachFeedForm.getThumbFileName(), "utf-8"))
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                .body(new FileSystemResource(file));
    }


    @ResponseBody
    @GetMapping("/images/{attachImgOid}")
    public Resource download(@PathVariable Long attachImgOid) throws MalformedURLException {
        AttachFeed attachFeed = attachFeedService.selectAttachImgById(attachImgOid);
        return new UrlResource("file:" + attachFeed.getSavedPath() + File.separator + attachFeed.getSavedFileName());
    }


    @ResponseBody
    @GetMapping("/profile/{profileId}")
    public Resource profileDownload(@PathVariable Long profileId) throws MalformedURLException {
        Attach attach = attachFeedService.selectProfileImgById(profileId);
        return new UrlResource("file:" + attach.getSavedPath() + File.separator + attach.getSavedFileName());
    }

    @ResponseBody
    @GetMapping("/character/{chatacterId}")
    public Resource characterDownload(@PathVariable Long chatacterId) throws MalformedURLException {
        Attach attach = attachFeedService.selectProfileImgById(chatacterId);
        return new UrlResource("file:" + attach.getSavedPath() + File.separator + attach.getSavedFileName());
    }

}
