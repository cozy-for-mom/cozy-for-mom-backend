package com.juju.cozyformombackend3.global.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.juju.cozyformombackend3.global.config.aws.AWSProperties;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import com.juju.cozyformombackend3.global.image.dto.response.UploadImageResponse;
import com.juju.cozyformombackend3.global.image.error.ImageErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3;
    private final AWSProperties awsProperties;

    @Override
    public UploadImageResponse upload(Long userId, MultipartFile image) {
        Optional<String> imageContentType = Optional.ofNullable(image.getContentType());
        if (imageContentType.isEmpty() || !imageContentType.get().contains("image")) {
            // TODO: file error로 고치기
            throw new BusinessException(ImageErrorCode.BAD_REQUEST_FILE_TYPE);
        }

        String originalFilename = image.getOriginalFilename();
        String extension = "";
        if (originalFilename != null) {
            int startPoint = originalFilename.lastIndexOf('.');
            extension = originalFilename.substring(startPoint);
        }
        String fileName = UUID.randomUUID() + extension;
        System.out.println("fileName = " + fileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(imageContentType.get());
        objectMetadata.setContentLength(image.getSize());

        System.out.println("bucket = " + awsProperties.getS3Bucket());
        try (InputStream inputStream = image.getInputStream()) {
            amazonS3.putObject(
                new PutObjectRequest(awsProperties.getS3Bucket(), fileName, inputStream, objectMetadata).withCannedAcl(
                    CannedAccessControlList.PublicRead));
        } catch (SdkClientException | IOException e) {
            log.error("File Upload Error", e);
            //TODO: http error code 찾기
            throw new BusinessException(ImageErrorCode.CONFLICT_FAIL_TO_UPLOAD_IMAGE);
        }
        return UploadImageResponse.of(amazonS3.getUrl(awsProperties.getS3Bucket(), fileName).toString());
    }

    @Override
    public void remove(String username, String imageUrl) {

    }
}
