//package com.yosep.website.component;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.yosep.website.entity.Product;
//import com.yosep.website.fileIO.S3Util;
//
//@RefreshScope
//@Component
//public class FileComponentS3 {
//	S3Util s3 = new S3Util();
//	String bucketName = "yoggaebi-msa-bucket";
//	
//	public void storeProductProfileImageFiles(Product product, MultipartFile file, String type, String detailType)
//			throws FileUploadException, IOException {
//		System.out.println(file.getOriginalFilename());
//		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//		String storePath = "yosep-spring-shoppingsite-msa-product" + File.separator + type + File.separator + detailType + File.separator + product.getProductId() + File.separator + "profile" + File.separator
//				+ file.getOriginalFilename();
//
//		if(file.isEmpty()) {
//			
//		}else {
//			byte[] productImageBytes = file.getBytes();
//			s3.fileUpload(bucketName, storePath, productImageBytes);
//			product.getProductProfileImageURLs().add(storePath);
//		}
//	}
//}
