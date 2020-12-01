package io.github.irishshagua.app.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public class Output {

    private static final Logger LOG = LoggerFactory.getLogger("AOC Logger");

    public static void print(String msg, LogTarget logTarget, LogLevel level) {
        switch (logTarget) {
            case CONSOLE_AND_LOGS -> {
                logger(msg, level);
                System.out.println(formatMsgForConsole(msg, level));
            }
            case CONSOLE_ONLY -> {
                System.out.println(formatMsgForConsole(msg, level));
            }
            case LOGS_ONLY -> {
                logger(msg, level);
            }
        }
    }

    private static String formatMsgForConsole(String msg, LogLevel logLevel) {
        String formatted = switch (logLevel) {
            case INFO -> CommandLine.Help.Ansi.AUTO.string(String.format("@|white %s|@", msg));
            case ERROR -> CommandLine.Help.Ansi.AUTO.string(String.format("@|bold,red %s|@", msg));
        };

        return formatted;
    }

    private static void logger(String msg, LogLevel logLevel) {
        switch (logLevel) {
            case INFO -> LOG.info(msg);
            case ERROR -> LOG.warn(msg);
        }
    }
}
