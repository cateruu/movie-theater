package com.pawelkrml.movies.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pawelkrml.movies.error.FileUploadException;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
  @Autowired
  private S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Value("${aws.s3.region}")
  private String region;

  @Value("${cdn.url}")
  private String cdnUrl;

  @Autowired
  private FileValidatorService fileValidator;

  public String uploadFile(MultipartFile file) {
    fileValidator.validateFile(file);

    try {
      return uploadAsWebp(file);
    } catch (IOException e) {
      throw new FileUploadException("Failed to upload file: " + e.getMessage(), e);
    }
  }

  private String uploadAsWebp(MultipartFile file) throws IOException {
    BufferedImage originalImage = ImageIO.read(file.getInputStream());
    if (originalImage == null) {
      throw new FileUploadException("Failed to read image file");
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    boolean success = ImageIO.write(originalImage, "webp", outputStream);
    if (!success) {
      throw new FileUploadException("Failed to convert image to WebP format");
    }

    String originalName = file.getOriginalFilename();
    String baseFileName = originalName;
    if (originalName != null && !originalName.isEmpty()) {
      baseFileName = originalName.substring(0, originalName.lastIndexOf('.'));
    }

    String webpFileName = generateFileName(baseFileName + ".webp");

    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(webpFileName)
        .contentType("image/webp")
        .cacheControl("public, max-age=31536000")
        .build();

    byte[] webpBytes = outputStream.toByteArray();
    s3Client.putObject(request,
        RequestBody.fromInputStream(
            new ByteArrayInputStream(webpBytes),
            webpBytes.length));

    return String.format("https://%s/%s", cdnUrl, webpFileName);
  }

  private String generateFileName(String originalFileName) {
    return UUID.randomUUID().toString() + "_" + originalFileName;
  }
}
