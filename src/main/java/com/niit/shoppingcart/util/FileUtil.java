package com.niit.shoppingcart.util;

import java.io.File;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	@Autowired
  HttpSession httpsession;
	
	@SuppressWarnings("unused")
	private static final Logger logger=LoggerFactory.getLogger(FileUtil.class);
	//private static final String imageDirectory="ShoppingCartImages";
	private static String rootPath = System.getProperty("divs.dir");
	
		public  boolean fileCopyNIO(MultipartFile file,String fileName) {
	System.out.println(rootPath);
			File dest = new File(rootPath+File.separator  + fileName);
			System.out.println("where it is uploading ??"+ dest.getAbsolutePath());
			/*if (!dest.exists()) {
				dest.mkdir();
			}*/

			try {
				file.transferTo(dest);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}

}