package br.com.stock;

import br.com.stock.main.Start;
import br.com.stock.model.Company;
import br.com.stock.model.Deal;
import br.com.stock.model.Monitoring;
import br.com.stock.repository.DealRepository;
import br.com.stock.service.MonitoringService;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;


@SpringBootApplication
public class ApplicationStart {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ApplicationStart.class, args);

        Start start = new Start();
        start.menu(context);

    }

}