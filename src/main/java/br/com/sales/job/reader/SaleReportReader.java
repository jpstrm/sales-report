package br.com.sales.job.reader;

import br.com.sales.config.SalesConfiguration;
import br.com.sales.domain.SaleReport;
import br.com.sales.mapper.SaleReportManMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class SaleReportReader {

    @Autowired
    private SalesConfiguration salesConfig;

    @Autowired
    private SaleReportManMapper saleReportManMapper;

    @Bean
    public ItemReader<SaleReport> reader() {
        DefaultLineMapper<SaleReport> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(salesConfig.getDelimiter()));
        lineMapper.setFieldSetMapper(saleReportManMapper);

        String path = System.getProperty(salesConfig.getPath()) + salesConfig.getPathIn() + "/data.dat";
        FlatFileItemReader<SaleReport> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(path));
        reader.setName("saleReportReader");
        reader.setLineMapper(lineMapper);

        return reader;
    }

}
