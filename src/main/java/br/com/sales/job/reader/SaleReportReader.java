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
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SaleReportReader {

    @Autowired
    private SalesConfiguration salesConfig;

    @Autowired
    private SaleReportManMapper saleReportManMapper;

    @Autowired
    private SalesReportService salesReportService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public MultiResourceItemReader<SaleReport> multiResourceItemReader() throws IOException {
        Resource[] inputFiles = getResources();
        MultiResourceItemReader<SaleReport> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(inputFiles);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    public FlatFileItemReader<SaleReport> reader() {
        System.out.println("reading");
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

    Resource[] getResources() throws IOException {
        final String path = System.getProperty(salesConfig.getPath()) + salesConfig.getPathIn() + "/*.dat";
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(path);
    }

}
