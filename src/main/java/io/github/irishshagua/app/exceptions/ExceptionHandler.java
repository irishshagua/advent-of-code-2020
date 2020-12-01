package io.github.irishshagua.app.exceptions;

import io.github.irishshagua.app.io.LogLevel;
import io.github.irishshagua.app.io.LogTarget;
import io.github.irishshagua.app.io.Output;
import picocli.CommandLine;

public class ExceptionHandler implements CommandLine.IExitCodeExceptionMapper {

    @Override
    public int getExitCode(Throwable exception) {
        if (exception instanceof TaskNotImplementedException tni) {
            Output.print("Stopping task execution as this task [" + tni.getTaskNumber() + "] in not yet implemented", LogTarget.CONSOLE_AND_LOGS, LogLevel.ERROR);
            return 10;
        } else if (exception instanceof TaskNotDocumentedException tnd) {
            Output.print("Unable to display task description for [" + tnd.getTaskNumber() + "]", LogTarget.CONSOLE_AND_LOGS, LogLevel.ERROR);
            return 11;
        } else {
            Output.print("Unexpected Error forced system to close", LogTarget.CONSOLE_ONLY, LogLevel.ERROR);
            Output.print("Caught Unhandled [" + exception.getMessage() + "]", LogTarget.LOGS_ONLY, LogLevel.ERROR);
            return 1;
        }
    }
}
