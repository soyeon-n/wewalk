package com.spring.boot.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.model.AttachmentType;
import com.spring.boot.model.UserFiles;

@Service
public class FileManager {

	@Value("${file.upload-dir}/")
    private String fileDirPath;
	
	//애플리케이션 시작 시 해당 디렉토리가 존재하는지 확인하고, 없으면 생성
	@PostConstruct
    public void init() {
        File f = new File(fileDirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
    }
	
	//전체 파일 묶음 세팅
	public List<UserFiles> saveFiles(List<MultipartFile> multipartFiles, AttachmentType attachmentType) throws IOException {
        List<UserFiles> attachments = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                attachments.add(saveFile(multipartFile, attachmentType));
            }
        }

        return attachments;
    }
	
	//실제 파일 저장
	public UserFiles saveFile(MultipartFile multipartFile, AttachmentType attachmentType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String savePath = createSaveFileName(originalFileName);
        
        //디렉토리에 파일 저장
        multipartFile.transferTo(new File(createPath(savePath, attachmentType)));

        //파일 저장 시 UserFiles 엔티티에 업로드 시간도 기록되게 세팅
        return UserFiles.builder()
        		.saveFileName(savePath)
                .originalFileName(originalFileName)
                .uploadTime(LocalDateTime.now())
                .attachmentType(attachmentType)
                .build();
    }

	//파일 디렉토리 생성
    public String createPath(String saveFileName, AttachmentType attachmentType) {
        String viaPath = (attachmentType == AttachmentType.IMAGE) ? "images/" : "generals/";
        
        String saveDir = fileDirPath+viaPath;
        
        File f = new File(saveDir);
        
        //해당 폴더 경로 없으면 생성
        if (!f.exists()) {
            f.mkdirs();
        }
        return saveDir + saveFileName;
    }

    //saveFileName 생성
    private String createSaveFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);
        String saveFileName = uuid + ext;

        return saveFileName;
    }

    //파일 확장자명 추출
    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(idx);
        return ext;
    }
}