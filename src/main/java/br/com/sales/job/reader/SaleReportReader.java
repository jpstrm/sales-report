package br.com.sales.job.reader;

import br.com.sales.config.SalesConfiguration;
import br.com.sales.domain.SaleReport;
import br.com.sales.mapper.SaleReportManMapper;
import br.com.sales.service.SalesReportService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class SaleReportReader {

    @Autowired
    private SalesConfiguration salesConfig;

    @Autowired
    private SaleReportManMapper saleReportManMapper;

    @Autowired
    private SalesReportService salesReportService;

    @Value("file:#{systemProperties['${config.path}']}${config.path-in}/${config.file-extension-pattern}")
    private Resource[] resources;

    @Bean
    public MultiResourceItemReader<SaleReport> multiResourceItemReader() throws IOException {
        Resource[] inputFiles = resources;
        MultiResourceItemReader<SaleReport> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(inputFiles);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<SaleReport> reader() {
        String path = System.getProperty(salesConfig.getPath()) + salesConfig.getPathIn() + "/data.dat";
        salesReportService.validateExtension(path);

        DefaultLineMapper<SaleReport> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(salesConfig.getDelimiter()));
        lineMapper.setFieldSetMapper(saleReportManMapper);

        FlatFileItemReader<SaleReport> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(path));
        reader.setName("saleReportReader");
        reader.setLineMapper(lineMapper);

        return reader;
    }

}
