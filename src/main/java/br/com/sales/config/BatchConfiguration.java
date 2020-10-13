package br.com.sales.config;

import br.com.sales.domain.SaleReport;
import br.com.sales.job.listener.SalesJobExecutingListener;
import br.com.sales.job.processor.SaleReportItemProcessor;
import br.com.sales.job.reader.SaleReportReader;
import br.com.sales.job.writer.SaleReportWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilder;

    @Autowired
    private StepBuilderFactory stepBuilder;

    @Autowired
    private SalesJobExecutingListener jobExecutingListener;

    @Autowired
    private SaleReportItemProcessor saleReportItemProcessor;

    @Autowired
    private SaleReportReader saleReportReader;

    @Autowired
    private SaleReportWriter saleReportWriter;

    @Bean
    public Job salesJob() {
        return jobBuilder.get("salesJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutingListener)
                .flow(salesStep1())
                .end()
                .build();
    }

    @Bean
    public Step salesStep1() {
        return stepBuilder.get("salesStep1")
                .<SaleReport, SaleReport>chunk(10)
                .reader(saleReportReader.reader())
                .processor(saleReportItemProcessor)
                .writer(saleReportWriter)
//                .faultTolerant()
//                .skip(FlatFileParseException.class)
//                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

}
