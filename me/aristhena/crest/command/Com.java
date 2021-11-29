package me.aristhena.crest.command;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Com {
    String[] names();
}
