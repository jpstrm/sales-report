package br.com.sales.service;

import br.com.sales.domain.Sale;
import br.com.sales.domain.SaleItem;
import br.com.sales.domain.SaleReport;
import br.com.sales.domain.SalesMan;
import br.com.sales.mapper.SaleMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

import static br.com.sales.helper.Helper.CUSTOMER_TYPE;
import static br.com.sales.helper.Helper.SALES_MAN_TYPE;
import static br.com.sales.helper.Helper.SALES_TYPE;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SalesReportServiceImpl.class)
public class SalesReportServiceImplTest {

    @InjectMocks
    private SalesReportServiceImpl service;

    @Mock
    private FieldSet fieldSet;

    @Mock
    private SaleMapper saleMapper;

    @Mock
    private SaleReport saleReport;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void shouldReadCustomerType() throws NoSuchFieldException {
        FieldSetter.setField(service, service.getClass().getDeclaredField("allowedExtension"), ".dat");
        FieldSetter.setField(service, service.getClass().getDeclaredField("saleReport"), saleReport);
        when(fieldSet.readString(0))
                .thenReturn(CUSTOMER_TYPE);

        service.read(fieldSet);

        verify(saleReport, times(1)).incrementCustomer();
    }

    @Test
    public void shouldReadSalesmanType() throws NoSuchFieldException {
        FieldSetter.setField(service, service.getClass().getDeclaredField("allowedExtension"), ".dat");
        when(fieldSet.readString(0))
                .thenReturn(SALES_MAN_TYPE);

        SaleReport saleReport = service.read(fieldSet);
        assertEquals(saleReport.getSalesManQty(), Integer.valueOf(1));
    }

    @Test
    public void shouldReadSalesType() throws NoSuchFieldException {
        FieldSetter.setField(service, service.getClass().getDeclaredField("allowedExtension"), ".dat");
        Sale sale = new Sale();
        SaleItem saleItem = new SaleItem();
        FieldSetter.setField(saleItem, saleItem.getClass().getDeclaredField("price"), BigDecimal.TEN);
        sale.setSaleItems(newArrayList(saleItem));
        when(fieldSet.readString(0))
                .thenReturn(SALES_TYPE);
        when(saleMapper.mapFieldSet(isA(FieldSet.class)))
                .thenReturn(sale);

        SaleReport saleReport = service.read(fieldSet);
        assertEquals(saleReport.getMostExpensiveSale().getTotal(), BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowErrorIfInvalidType() throws NoSuchFieldException {
        FieldSetter.setField(service, service.getClass().getDeclaredField("allowedExtension"), ".dat");
        when(fieldSet.readString(0))
                .thenReturn("04");

        service.read(fieldSet);
    }

    @Test
    public void shouldWrite() throws NoSuchFieldException, IOException {
        Sale sale = new Sale();
        SaleItem saleItem = new SaleItem();
        sale.setSaleItems(newArrayList(saleItem));
        SalesMan salesMan = new SalesMan();

        FieldSetter.setField(saleItem, saleItem.getClass().getDeclaredField("price"), BigDecimal.TEN);
        FieldSetter.setField(service, service.getClass().getDeclaredField("saleReport"), saleReport);
        FieldSetter.setField(salesMan, salesMan.getClass().getDeclaredField("name"),"Test");
        when(saleReport.getMostExpensiveSale())
                .thenReturn(sale);
        when(saleReport.getWorstSalesMan())
                .thenReturn(salesMan);

        service.write(Writer.nullWriter());

        verify(saleReport, times(1)).getCustomerQty();
        verify(saleReport, times(1)).getSalesManQty();
        verify(saleReport, times(1)).getMostExpensiveSale();
        verify(saleReport, times(1)).getWorstSalesMan();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateExtension() throws NoSuchFieldException {
        FieldSetter.setField(service, service.getClass().getDeclaredField("allowedExtension"), ".dat");

        service.validateExtension(".test");
    }

}
