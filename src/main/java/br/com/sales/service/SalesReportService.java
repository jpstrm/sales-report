package br.com.sales.service;

import br.com.sales.domain.SaleReport;
import org.springframework.batch.item.file.transform.FieldSet;

import java.io.IOException;
import java.io.Writer;

public interface SalesReportService {

    SaleReport read(FieldSet fieldSet);
    void write(Writer writer) throws IOException;
    void validateExtension(String path);

}
