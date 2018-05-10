package com.saveondev.documents.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saveondev.documents.models.Document;

public interface DocumentService {

    public List<Document> getDocuments();

    public Document uploadDocument(MultipartFile file, String description);

    public Document rejectDocument(String uuid);

    public Document approveDocument(String uuid);

    public void deleteDocument(String uuid);

}
