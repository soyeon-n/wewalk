package com.spring.boot.model;

import org.springframework.stereotype.Component;

@Component // 파일업로드를 도와주는 클래스-컴포넌트 분리 
public class FileStore {
	
	private final String rootPath = System.getProperty("user.dir");//파일경로 ? 

}
