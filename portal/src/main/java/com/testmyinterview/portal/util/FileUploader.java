package com.testmyinterview.portal.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/**
 * This class is used for file uploading
 * 
 * 
 * 
 */
public class FileUploader {

	private static final Logger logger = LoggerFactory
			.getLogger(FileUploader.class);

	private String fileName = "";
	private String uploadFile = "";

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * this method is to upload file to amazon with access key and secret key
	 * credentials
	 * 
	 * @param file
	 * @param destFile
	 */
	public void amazonTransfer(MultipartFile file) {
		ClassPathResource cpr = new ClassPathResource("app.properties");
		Properties prop = new Properties();
		try {
			prop.load(cpr.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String keyName = getFileName();
		String myAccessKeyID = (String) prop.get("s3AccessKey");
		String mySecretKey = (String) prop.get("s3SecretKey");
		AWSCredentials myCredentials = new BasicAWSCredentials(myAccessKeyID,
				mySecretKey);
		TransferManager tm = new TransferManager(myCredentials);

		Upload upload = null;
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		try {
			upload = tm.upload((String) prop.get("s3bucket"), keyName,
					file.getInputStream(), metadata);
		} catch (AmazonServiceException e1) {

			e1.printStackTrace();
		} catch (AmazonClientException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			upload.waitForCompletion();
		} catch (AmazonClientException amazonClientException) {
			amazonClientException.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to get the max file upload size
	 * 
	 * @return maxFileUploadLimit
	 */
	public long maxFileSize() {
		ClassPathResource cpr = new ClassPathResource("app.properties");
		Properties prop = new Properties();
		try {
			prop.load(cpr.getInputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}
		return Long.parseLong((String) prop.get("maxFileUploadLimit"));
	}

	/**
	 * This method is used to get the local system path to upload the file
	 * 
	 * @return (String) prop.get("destinationDir")
	 */
	public String destinationUrl() {
		ClassPathResource cpr = new ClassPathResource("app.properties");
		Properties prop = new Properties();
		try {
			prop.load(cpr.getInputStream());
		} catch (IOException e) {
		}
		return (String) prop.get("destinationDir");
	}

	/**
	 * this method is used to upload multipart file to amazon s3 bucket
	 * 
	 * @param req
	 * @param fileType
	 */
	public void fileUploadFromController(HttpServletRequest request,
			String uploadFile) {
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		Enumeration mrEnum = mr.getParameterNames();
		MultipartFile file = mr.getFile(uploadFile);
		setUploadFile(uploadFile);
		if (file != null && file.getSize() > 0
				&& file.getSize() < maxFileSize()) {
			try {
				request.setAttribute(getUploadFile(),
						new String(file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileName = file.getOriginalFilename();
			String upldFile = fileName;
			upldFile = upldFile.substring(upldFile.indexOf('.'),
					upldFile.length());
			long timestamp = new Timestamp(new Date().getTime()).getTime();
			if (upldFile == null || upldFile.isEmpty()) {
				setFileName(null);
			} else {
				setFileName(timestamp + upldFile);
			}
			amazonTransfer(file);
		} else {
			if (file != null && file.getSize() > maxFileSize()) {
				throw new org.springframework.web.multipart.MaxUploadSizeExceededException(
						maxFileSize());
			}
		}
	}
}