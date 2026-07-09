package com.financialos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialOsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialOsApplication.class, args);
        System.out.println("=================================================");
        System.out.println(" Financial OS is running!");
        System.out.println(" Try: http://localhost:8080/api/dashboard");
        System.out.println("=================================================");
    }
}
