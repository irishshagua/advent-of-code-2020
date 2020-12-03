package io.github.irishshagua.app.exceptions;

public class TaskNotImplementedException extends RuntimeException {

    private Integer taskNumber;

    public TaskNotImplementedException(Integer taskNumber) {
        super("Task ["+taskNumber+"] not implemented");
        this.taskNumber = taskNumber;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }
}
