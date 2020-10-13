package br.com.sales.service;

import br.com.sales.domain.Sale;
import br.com.sales.domain.SaleReport;
import br.com.sales.mapper.SaleMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;

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
    public SaleReport read(FieldSet fieldSet) {
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
    public void write(Writer writer) throws IOException {
        writer.append("Quantidade de clientes no arquivo de entrada: ")
                .append(String.valueOf(saleReport.getCustomerQty())).append("\n");
        writer.append("Quantidade de vendedor no arquivo de entrada: ")
                .append(String.valueOf(saleReport.getCustomerQty())).append("\n");
        writer.append("ID da venda mais cara: ")
                .append(String.valueOf(saleReport.getMostExpensiveSale().getId())).append("\n");
        writer.append("O pior vendedor: ")
                .append(String.valueOf(saleReport.getWorstSalesMan().getName())).append("\n");
    }

    public void validateExtension(String path) {
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
