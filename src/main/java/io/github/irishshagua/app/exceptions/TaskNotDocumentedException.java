package io.github.irishshagua.app.exceptions;

public class TaskNotDocumentedException extends RuntimeException {

    private Integer taskNumber;

    public TaskNotDocumentedException(Integer taskNumber) {
        super("Task ["+taskNumber+"] not documented");
        this.taskNumber = taskNumber;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }
}
