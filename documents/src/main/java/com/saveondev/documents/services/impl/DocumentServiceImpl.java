package com.saveondev.documents.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.saveondev.documents.common.Constants;
import com.saveondev.documents.models.Document;
import com.saveondev.documents.repositories.DocumentRepository;
import com.saveondev.documents.services.DocumentService;
import com.saveondev.documents.utils.GoogleCloudStorageUtils;

@Component
public class DocumentServiceImpl implements DocumentService {

    @Value("${cloud.storage.bucket.name:saveondev}")
    private String bucketName;

    @Autowired
    DocumentRepository documentRepo;

    @Override
    public List<Document> getDocuments() {
        return (List<Document>) this.documentRepo.findAll();
    }

    @Override
    public Document uploadDocument(MultipartFile file, String description) {

        // Upload file to google cloud storage.
        String downloadUrl = GoogleCloudStorageUtils.uploadFieToCloudStorage(file, this.bucketName);

        // Save document to database.
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFileSize(file.getSize());
        document.setDescription(description);
        document.setDownloadUrl(downloadUrl);
        document.setStatus(Constants.DOCUMENT_SUBMITTED);

        return this.documentRepo.save(document);
    }

    @Override
    public Document rejectDocument(String uuid) {
        Document doc = this.documentRepo.findOne(UUID.fromString(uuid));
        doc.setStatus(Constants.DOCUMENT_REJECTED);
        return this.documentRepo.save(doc);
    }

    @Override
    public Document approveDocument(String uuid) {
        Document doc = this.documentRepo.findOne(UUID.fromString(uuid));
        doc.setStatus(Constants.DOCUMENT_APPROVED);
        return this.documentRepo.save(doc);
    }

    @Override
    public void deleteDocument(String uuid) {
        // Delete on google cloud storage.
        Document doc = this.documentRepo.findOne(UUID.fromString(uuid));
        GoogleCloudStorageUtils.deleteFileOnCloudStorage(doc.getFileName(), this.bucketName);
        // Delete on database
        this.documentRepo.delete(UUID.fromString(uuid));
    }
}
