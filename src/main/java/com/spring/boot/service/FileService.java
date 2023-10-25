package com.spring.boot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.dao.FileRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.AttachmentType;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserFiles;
import com.spring.boot.util.FileManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService{

	private final FileRepository fileRepository;
	private final FileManager fileManager;
	
	//첨부파일을 UserFiles에 저장하는 메소드
	//fileManager를 통해 이미지 파일과 일반 파일을 업로드 및 db에 저장한다.
	//업로드 및 저장 처리가 완료된 첨부파일들을 넘겨준다.
	public List<UserFiles> saveAttachments(Map<AttachmentType, List<MultipartFile>> multipartFileListMap, SiteUser user) throws IOException{
	    List<UserFiles> imageFiles = fileManager.saveFiles(multipartFileListMap.get(AttachmentType.IMAGE), AttachmentType.IMAGE);
	    List<UserFiles> generalFiles = fileManager.saveFiles(multipartFileListMap.get(AttachmentType.GENERAL), AttachmentType.GENERAL);
	    List<UserFiles> result = Stream.of(imageFiles, generalFiles)
	            .flatMap(f -> f.stream())
	            .map(f -> {
	                f.setUser(user);
	                return f;
	            })
	            .collect(Collectors.toList());
	    
	    fileRepository.saveAll(result);
	    
	    return result;
	}
	
	//파일 로드
	public Map<AttachmentType, List<UserFiles>> findAttachments() {
	    List<UserFiles> attachments = fileRepository.findAll();
	    Map<AttachmentType, List<UserFiles>> result = attachments.stream()
	            .collect(Collectors.groupingBy(UserFiles::getAttachmentType));

	    return result;
	}
	
}
