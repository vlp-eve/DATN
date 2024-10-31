package com.poly.datn;

import com.poly.datn.Service.DiscountService;
import com.poly.datn.Service.Impl.StoreServiceImpl;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class DatnApplication {


	public static void main(String[] args) {
		SpringApplication.run(DatnApplication.class, args);

	}


}
