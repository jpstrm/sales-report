package br.com.sales.job.writer;

import br.com.sales.config.SalesConfiguration;
import br.com.sales.domain.SaleReport;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleReportWriter extends AbstractItemStreamItemWriter<SaleReport> {

    @Autowired
    private SalesConfiguration salesConfig;

    @Override
    public void write(List<? extends SaleReport> items) throws Exception {
        FlatFileItemWriter<SaleReport> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource(salesConfig.getPathOut() + "/data.done.dat"));
        itemWriter.setAppendAllowed(true);
        itemWriter.setLineAggregator(createSaleReportLineAggregator());
    }

    private LineAggregator<SaleReport> createSaleReportLineAggregator() {
        DelimitedLineAggregator<SaleReport> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        lineAggregator.setFieldExtractor(getFieldExtractor());

        return lineAggregator;
    }

    private FieldExtractor<SaleReport> getFieldExtractor() {
        return new BeanWrapperFieldExtractor<>(){
            {
                setNames(new String[] { "name", "test", "test2" });
            }
        };
    }

}
