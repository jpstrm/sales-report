package br.com.sales.service;

import br.com.sales.domain.Sale;
import br.com.sales.domain.SaleReport;
import br.com.sales.mapper.SaleMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.sales.helper.Helper.CUSTOMER_TYPE;
import static br.com.sales.helper.Helper.SALES_MAN_TYPE;
import static br.com.sales.helper.Helper.SALES_TYPE;

@Service
public class SalesReportServiceImpl implements SalesReportService {

    @Value("${config.allowed-extension}")
    private String allowedExtension;

    @Autowired
    private SaleMapper saleMapper;

    private final SaleReport saleReport = new SaleReport();

    @Override
    public String read(String path) {
        validateExtension(path);

        return null;
    }

    @Override
    public SaleReport process(FieldSet fieldSet) {
        switch (fieldSet.readString(0)) {
            case CUSTOMER_TYPE:
                saleReport.incrementCustomer();
                break;
            case SALES_MAN_TYPE:
                saleReport.incrementSalesMan();
                break;
            case SALES_TYPE:
                Sale sale = saleMapper.mapFieldSet(fieldSet);
                saleReport.updateMostExpensiveSale(sale);
                break;
            default:
                throw new IllegalArgumentException("Invalid data type");
        }

        return saleReport;
    }

    @Override
    public void write(List<SaleReport> saleReports, String path, String fileName) {

    }

    private void validateExtension(String path) {
        if (!path.toLowerCase().endsWith(allowedExtension)) {
            String errorMsg = new StringBuilder()
                    .append("This file is not allowed. ")
                    .append(" Allowed files: ")
                    .append(allowedExtension)
                    .toString();
            throw new IllegalArgumentException(errorMsg);
        }
    }

}
