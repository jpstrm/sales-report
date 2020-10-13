package br.com.sales.job.processor;

import br.com.sales.domain.SaleReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleReportItemProcessor implements ItemProcessor<SaleReport, SaleReport> {

    @Override
    public SaleReport process(SaleReport item) {
        log.info("Processing SaleReport {}", item);

        return null;
    }

}
