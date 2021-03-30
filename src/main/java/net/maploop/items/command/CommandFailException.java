package net.maploop.items.command;

public class CommandFailException extends RuntimeException {
    final String s;
    public CommandFailException(String s) {
        this.s = s;
    }
}
