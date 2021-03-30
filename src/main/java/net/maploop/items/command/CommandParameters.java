package net.maploop.items.command;

public @interface CommandParameters {
    String description() default "";
    String usage() default "/<command>";
    String aliases() default "";
    CommandSource source() default CommandSource.ANY;
    boolean root() default false;
}
