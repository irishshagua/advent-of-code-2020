package io.github.irishshagua.app.exceptions;

public class TaskNotDocumentedException extends Exception {

    private Integer taskNumber;

    public TaskNotDocumentedException(Integer taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    public TaskNotDocumentedException(Integer taskNumber, Exception e) {
        super(e);
        this.taskNumber = taskNumber;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }
}
