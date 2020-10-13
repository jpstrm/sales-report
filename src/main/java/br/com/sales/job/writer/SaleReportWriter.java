package br.com.sales.job.writer;

import br.com.sales.config.SalesConfiguration;
import br.com.sales.domain.SaleReport;
import br.com.sales.service.SalesReportService;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class SaleReportWriter {

    @Autowired
    private SalesConfiguration salesConfig;

    @Autowired
    private SalesReportService salesReportService;

    @Bean
    public FlatFileItemWriter<SaleReport> itemWriter() {
        String path = System.getProperty(salesConfig.getPath()) + salesConfig.getPathOut() + "/data.done.dat";

        return  new FlatFileItemWriterBuilder<SaleReport>()
                .name("saleReportItemWriter")
                .resource(new FileSystemResource(path))
                .lineAggregator(new PassThroughLineAggregator<>())
                .footerCallback(getFooterCallback())
                .build();
    }

    @Bean
    private FlatFileFooterCallback getFooterCallback() {
        return writer -> salesReportService.write(writer);
    }

}
