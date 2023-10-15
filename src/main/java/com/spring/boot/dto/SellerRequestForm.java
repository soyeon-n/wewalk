package com.spring.boot.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.model.AttachmentType;
import com.spring.boot.model.SellerRequest;
import com.spring.boot.model.SiteUser;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SellerRequestForm {

	@NotEmpty
	private String intro;
	
	private List<MultipartFile> imageFiles;
	private List<MultipartFile> generalFiles;
	
	@Builder
    public SellerRequestForm(String intro, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.intro = intro;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }

	//다중의 이미지 파일과 다중의 일반 파일을 업로드
    public Map<AttachmentType, List<MultipartFile>> getAttachmentTypeListMap() {
        Map<AttachmentType, List<MultipartFile>> attachments = new ConcurrentHashMap<>();
        attachments.put(AttachmentType.IMAGE, imageFiles);
        attachments.put(AttachmentType.GENERAL, generalFiles);
        return attachments;
    }
	
}
