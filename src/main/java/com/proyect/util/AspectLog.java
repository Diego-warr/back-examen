package com.proyect.util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLog {

    @Before("execution(* getAll())")
    public void log()
    {
        System.out.println("hola");
    }
}
