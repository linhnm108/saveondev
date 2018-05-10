package com.saveondev.documents.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.saveondev.documents.models.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, UUID> {
}
