package com.sci.Trip;

//import com.sci.Trip.Controller.FileUploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.sci.Trip","controller"})
public class TripAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripAppApplication.class, args);
	}

}
