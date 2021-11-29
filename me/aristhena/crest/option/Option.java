package me.aristhena.crest.option;

import me.aristhena.crest.module.*;
import java.util.*;
import java.lang.annotation.*;

public class Option<T>
{
    private String id;
    private String name;
    private T value;
    private Module module;
    private List<Option> optionList;
    
    public Option(final String id, final String name, final T value, final Module module) {
        this.optionList = new ArrayList<Option>();
        this.id = id;
        this.name = name;
        this.value = value;
        this.module = module;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getDisplayName() {
        return this.name;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public List<Option> getOptions() {
        return this.optionList;
    }
    
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Op {
        String name() default "null";
        
        double min() default 1.0;
        
        double max() default 10.0;
        
        double increment() default 1.0;
    }
}
