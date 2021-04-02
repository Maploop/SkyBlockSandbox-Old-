package net.maploop.items.enums;

public enum Message {
    NO_PERMISSION("§cYou must be admin or higher to use this command!"),
    ONLY_IN_GAME("§cConsole senders are not allowed to execute this command!"),
    ONLY_CONSOLE("§cOnly console senders are allowed to execute this command!"),
    PLAYER_NOT_FOUND("§cPlayer not found."),
    CONFIG_ERROR("There is an issue with a configuration entry. Please contact the server's administrator."),
    MISSING_ARGUMENTS("§cMissing arguments.");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
