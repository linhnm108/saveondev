package com.saveondev.documents.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalTask {
    private String taskId;
    private Document document;
}
