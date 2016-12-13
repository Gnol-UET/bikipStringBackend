package uet.k59t.service;

/**
 * Created by Long on 11/20/2016.
 */

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String sayHello(String s){
        return "Hello, " +s;
    }
}