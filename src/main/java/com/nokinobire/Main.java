package com.nokinobire;

import com.nokinobire.core.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static final String SPRING_CONTEXT_FILE = "spring-context.xml";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE);
        Application application = context.getBean(Application.class);
        application.run();
    }
}
