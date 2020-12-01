package io.github.irishshagua.app.exceptions;

public class TaskNotImplementedException extends Exception {

    private Integer taskNumber;

    public TaskNotImplementedException(Integer taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }
}
