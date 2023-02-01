package top.catium.csp.logging;

import top.catium.csp.file.TextFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalLogger {


    private File _logDir;

    public LocalLogger(String logDirPath) {
        _logDir = new File(logDirPath);
        if (!_logDir.exists()) {
            _logDir.mkdirs();
        }
        info("log directory is setting to " + _logDir.getPath());
    }

    public LocalLogger(File logDir) {
        _logDir = logDir;
        if (!_logDir.isDirectory()) {
            _logDir.mkdirs();
        }
        info("log directory is setting to " + _logDir.getPath());
    }

    public void log(String str, String label) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        str = currentTime + " [" + label + "] " + str + System.lineSeparator();
        System.out.print(str);
        TextFile.append(_logDir.getPath() + "/" + label + ".log", str);
        TextFile.append(_logDir.getPath() + "/all.log", str);
    }

    public void info(String str) {
        log(str, "info");
    }

    public void warn(String str) {
        log(str, "warn");
    }

    public void error(String str) {
        log(str, "error");
    }

    public void fatal(String str) {
        log(str, "fatal");
    }
}
