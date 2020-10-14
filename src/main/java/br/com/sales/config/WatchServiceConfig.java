package br.com.sales.config;

import br.com.sales.helper.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Configuration
@Slf4j
public class WatchServiceConfig {

    @Autowired
    private SalesConfiguration salesConfig;

    @Conditional(FolderCondition.class)
    @Bean
    public WatchService watchService() throws IOException {
        String path = Helper.getPathIn(salesConfig);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get(path).register(watchService, ENTRY_CREATE);
        log.info("Watching folder: " + path);

        return watchService;
    }

}
