package br.com.sales.mapper;

import br.com.sales.domain.SaleReport;
import br.com.sales.service.SalesReportService;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleReportManMapper implements FieldSetMapper<SaleReport> {

    @Autowired
    private SalesReportService salesReportService;

    @Override
    public SaleReport mapFieldSet(FieldSet fieldSet) {
        return salesReportService.read(fieldSet);
    }

}
