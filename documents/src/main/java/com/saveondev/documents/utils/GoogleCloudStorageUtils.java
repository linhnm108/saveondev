package com.saveondev.documents.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GoogleCloudStorageUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleCloudStorageUtils.class);

    private static Storage storage = null;

    // [START init]
    static {
        try {
            InputStream credentialsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Documents-467e033da906.json");
            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(credentialsStream))
                    .build()
                    .getService();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public static String uploadFieToCloudStorage(MultipartFile file, String bucketName) {
        String downloadUrl = StringUtils.EMPTY;
        String fileName = file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
        try {
            Blob blob = storage.create(blobInfo, file.getBytes());
            return blob.getMediaLink();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return downloadUrl;
    }

    public static boolean deleteFileOnCloudStorage(String fileName, String bucketName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        return storage.delete(blobId);
    }
}
