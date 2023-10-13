package com.spring.boot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
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
	
	//전체 파일 저장
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
        
        multipartFile.transferTo(new File(createPath(savePath, attachmentType)));

        return UserFiles.builder()
        		.saveFileName(savePath)
                .originalFileName(originalFileName)
                .uploadTime(LocalDateTime.now())
                .attachmentType(attachmentType)
                .build();

    }

	//파일 경로 구성
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

    //
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
	
	//파일 업로드(참고용)
//	public static String doFileUpload(InputStream is, String originalFileName, String path) throws Exception{
//		
//		String newFileName = "";
//		
//		//파일 업로드 안시키고 버튼 누르면
//		if(is==null) {
//			return null;
//		}
//		
//		if(originalFileName.equals("")) {
//			return null;
//		}
//		
//		//abc.txt
//		//0123456			
//		//뒤에서부터 .을 찾아서 그 뒤를 넣어줌
//		String fileExt = 
//				originalFileName.substring(originalFileName.lastIndexOf("."));
//		
//		if(fileExt==null || fileExt.equals("")) {
//			return null;
//		}
//		
//		//새로운 파일명 생성(연월일시분초)
//		newFileName = 
//				String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
//		
//		newFileName += System.nanoTime();//절대 중복 안되게 나노타임으로 구분
//		//newFileName += fileExt;
//		
//		File f = new File(path);
//		
//		if(!f.exists()) {
//			f.mkdirs();
//		}
//		
//		String fullFilePath = path + File.separator + newFileName;
//		
//		//파일 업로드(사용자가 업로드한 파일의 데이터를 다 담음)
//		FileCopyUtils.copy(is, new FileOutputStream(fullFilePath));
//		
//		//DB에 저장하기 위해 파일명 리턴
//		return newFileName;
//		
//	}
	
}
