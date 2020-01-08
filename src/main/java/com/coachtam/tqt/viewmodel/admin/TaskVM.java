package com.coachtam.tqt.viewmodel.admin;

public class TaskVM {

    private String id;
    private String name;
    private String description;
    private String assignee;
    private String processInstanceId;
    private String createBy;

    public TaskVM() {
    }



    public TaskVM(String id, String name, String description, String assignee, String processInstanceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.processInstanceId = processInstanceId;
    }

    public TaskVM(String id, String name, String description, String assignee, String processInstanceId, String createBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.processInstanceId = processInstanceId;
        this.createBy = createBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
