package com.rdev.izat.lb2;

public interface ILogger {
    void debug(String message, Object... fmt);
    void info(String message, Object... fmt);
    void error(String message, Object... fmt);
}
