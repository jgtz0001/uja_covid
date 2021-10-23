package es.ujaen.dae.ujacovid.app;

import java.io.*;
import java.text.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "es.ujaen.dae.ujacovid.beans")
@EntityScan(basePackages = "es.ujaen.dae.ujacovid.entidades")
public class UjacovidApplication {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        SpringApplication servidor = new SpringApplication(UjacovidApplication.class);
        ApplicationContext context = servidor.run(args);

    }
}