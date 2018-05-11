package com.saveondev.documents.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.saveondev.documents.common.Constants;
import com.saveondev.documents.models.ApprovalTask;
import com.saveondev.documents.models.Document;

@Controller
public class UserTaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value="/tasks", method = RequestMethod.GET)
    public String approveTask(Model model) {
        List<Task> tasks = taskService.createTaskQuery().active().list();
        model.addAttribute("tasks", convertToApprovalTask(tasks));
        return "tasks";
    }

    @RequestMapping(value="/task/approve", method = RequestMethod.POST)
    public String approveTask(@RequestParam String taskId) {
        this.taskService.complete(taskId, Collections.singletonMap("status", Constants.DOCUMENT_APPROVED));
        return "redirect:/tasks";
    }

    @RequestMapping(value="/task/reject", method = RequestMethod.POST)
    public String rejectTask(@RequestParam String taskId) {
        this.taskService.complete(taskId, Collections.singletonMap("status", Constants.DOCUMENT_REJECTED));
        return "redirect:/tasks";
    }

    private List<ApprovalTask> convertToApprovalTask(List<Task> tasks) {
        List<ApprovalTask> approvalTasks = new ArrayList<>();
        for (Task task : tasks) {
            ApprovalTask approvalTask = new ApprovalTask();
            approvalTask.setTaskId(task.getId());
            Document doc = (Document) taskService.getVariable(task.getId(), "document");
            approvalTask.setDocument(doc);
            approvalTasks.add(approvalTask);
        }
        return approvalTasks;
    }
}
