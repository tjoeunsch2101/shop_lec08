package com.tjoeun.shop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	// 파일 업로드하기
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
		
		UUID uuid = UUID.randomUUID();
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		fos.close();
		
		return savedFileName;
	}
	
	// 파일 삭제하기
	public void deleteFile(String filePath) {
		
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info(">>>>>>>>>>>>>>>> 파일 삭제 완료 !!!");
		}else {
			log.info(">>>>>>>>>>>>>>>> 해당 파일이 없어서 삭제 못함 !!!");
		}
		
	}
	
	
	

}
