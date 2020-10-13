package br.com.sales.service;

import br.com.sales.domain.SaleReport;
import org.springframework.batch.item.file.transform.FieldSet;

import java.io.IOException;
import java.util.List;

public interface SalesReportService {

    String read(String path) throws Exception;
    SaleReport process(FieldSet fieldSet);
    void write(List<SaleReport> saleReports, String path, String fileName);

}
