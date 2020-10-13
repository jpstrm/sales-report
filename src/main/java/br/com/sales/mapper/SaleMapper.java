package br.com.sales.mapper;

import br.com.sales.domain.Sale;
import br.com.sales.domain.SalesMan;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper implements FieldSetMapper<Sale> {

    @Autowired
    SaleItemsMapper saleItemMapper;

    @Override
    public Sale mapFieldSet(FieldSet fieldSet) {
        try {
            return Sale.builder()
                    .id(fieldSet.readLong(1))
                    .saleItems(saleItemMapper.mapFieldSet(fieldSet.readString(2)))
                    .salesMan(new SalesMan(fieldSet.readString(3)))
                    .build();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Sale item invalid or corrupted " + ex.getLocalizedMessage());
        }
    }

}
