package pl.michaeli.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.michaeli.spring.config.AppConfig;

public class SingletonAppContext {
	
    private static AnnotationConfigApplicationContext context;
    
    private SingletonAppContext() {}
 
    public static AnnotationConfigApplicationContext getContext() {
        if(context == null){
        	context = new AnnotationConfigApplicationContext(AppConfig.class);;
        }
        return context;
    }
}
