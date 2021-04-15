package net.maploop.items.command.exception;

public class CommandFailException extends RuntimeException {
    final String s;
    public CommandFailException(String s) {
        this.s = s;
    }

    @Override
    public String getMessage(){
        return this.s;
    }
}
