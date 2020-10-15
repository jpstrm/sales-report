package br.com.sales;

import br.com.sales.service.WatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@SpringBootApplication
@EnableAsync
public class SalesBatchApplication implements CommandLineRunner {

    @Autowired
    public WatcherService watcherService;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SalesBatchApplication.class, args);

        try {
            context.getBean("watchService");
        } catch (NoSuchBeanDefinitionException exception) {
            context.close();
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Watcher service running...");

        new Thread(watcherService, "watcher-service").start();
    }

}
