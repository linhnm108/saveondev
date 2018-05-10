package com.saveondev.documents.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saveondev.documents.models.Document;
import com.saveondev.documents.services.DocumentService;

@Controller
public class DocumentController {

    public static final String NEW_DOC = "new_doc";
    public static final String REJECTED_DOC = "rejected_doc";
    public static final String APPROVED_DOC = "approved_doc";
    public static final String DELETED_DOC = "deleted_doc";

    @Autowired
    private DocumentService documentService;

    /**
     * Upload document.
     * @param file
     * @param description
     * @param redir
     * @return
     */
    @RequestMapping(value = "/document/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(name="file") MultipartFile file, @RequestParam(name="description") String description, RedirectAttributes redir) {
        Document document = this.documentService.uploadDocument(file, description);
        redir.addAttribute(NEW_DOC, document.getFileName());
        return "redirect:/";
    }

    @RequestMapping(value="/document/upload", method = RequestMethod.GET)
    public String upload() {
        return "document-upload";
    }

    /**
     * Reject document.
     * @param uuid
     * @param redir
     * @return
     */
    @RequestMapping(value = "/document/reject", method = RequestMethod.POST)
    public String reject(@RequestParam(name="uuid") String uuid, RedirectAttributes redir) {
        Document document = this.documentService.rejectDocument(uuid);
        redir.addAttribute(REJECTED_DOC, document.getFileName());
        return "redirect:/";
    }

    /**
     * Approve document.
     * @param uuid
     * @param redir
     * @return
     */
    @RequestMapping(value = "/document/approve", method = RequestMethod.POST)
    public String approve(@RequestParam(name="uuid") String uuid, RedirectAttributes redir) {
        Document document = this.documentService.approveDocument(uuid);
        redir.addAttribute(APPROVED_DOC, document.getFileName());
        return "redirect:/";
    }

    /**
     * Approve document.
     * @param uuid
     * @param redir
     * @return
     */
    @RequestMapping(value = "/document/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name="uuid") String uuid, RedirectAttributes redir) {
        this.documentService.deleteDocument(uuid);
        redir.addAttribute(DELETED_DOC, uuid);
        return "redirect:/";
    }

    @RequestMapping("/")
    public String getAllDocuments(Model model, HttpServletRequest request) {
        List<Document> documents = this.documentService.getDocuments();
        model.addAttribute("documents", documents);
        model.addAttribute(NEW_DOC, request.getParameter(NEW_DOC));
        model.addAttribute(REJECTED_DOC, request.getParameter(REJECTED_DOC));
        model.addAttribute(APPROVED_DOC, request.getParameter(APPROVED_DOC));
        model.addAttribute(DELETED_DOC, request.getParameter(DELETED_DOC));
        return "documents";
    }
}
