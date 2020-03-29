package com.yosep.website.component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.yosep.website.entity.Product;

@RefreshScope
@Component
public class FileComponent {
	@Value("${file.upload-dir}")
	private static String root = "/Users/jeon-yoseb/documents/yosep-spring-shoppingsite-msa-product2";

	private Path rootPath;

	public FileComponent() {

	}

	public void storeProductProfileImageFiles(Product product, MultipartFile file, String type, String detailType)
			throws FileUploadException {
		System.out.println("root:" + root);
		System.out.println(file.getOriginalFilename());
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String storePath = root + "/" + type + "/" + detailType + "/" + product.getProductId() + "/profile/"
				+ file.getOriginalFilename();


		rootPath = Paths.get(storePath);

		try {
			Files.createDirectories(this.rootPath);
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}

		try {
			Path targetLocation = Paths.get(storePath).toAbsolutePath().normalize();
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			product.getProductProfileImageURLs().add(storePath);
		} catch (Exception e) {
			throw new FileUploadException("[" + product.getProductId() + "] 파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
		}
	}

	public void storeProductDescriptionImageFiles(Product product, MultipartFile file, String type, String detailType)
			throws FileUploadException {
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String storePath = root + "/" + type + "/" + detailType + "/" + product.getProductId() + "/description/"
				+ file.getOriginalFilename();

		rootPath = Paths.get(storePath);
		
		try {
			Files.createDirectories(this.rootPath);
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}

		try {
			Path targetLocation = Paths.get(storePath).toAbsolutePath().normalize();
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {

		}
	}
}
