package com.blog.main.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.main.services.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();
		
//		random name genrate file
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
//		fullPath
		String filepath=path+File.separator+fileName1;
		
//		create Folder if not Created
		File f=new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
//		File Copy
		Files.copy(file.getInputStream(), Paths.get(filepath));
		return fileName1;
	}

	@Override
	public InputStream getResources(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream InputStream = new FileInputStream(fullPath);
//		db logic to return inputStream
		return InputStream;
	}

}
