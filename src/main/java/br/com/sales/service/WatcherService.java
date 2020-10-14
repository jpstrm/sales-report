package br.com.sales.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Service
@Slf4j
public class WatcherService implements Runnable {

    @Autowired
    private WatchService watchService;

    @Override
    public void run() {
        while (true) {
            log.info("Running watch service");
            try {
                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        log.info(
                                "Event kind:" + event.kind()
                                        + ". File affected: " + event.context() + ".");
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
