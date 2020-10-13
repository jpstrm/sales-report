package br.com.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SalesMan {

    private String cpf;
    private String name;
    private BigDecimal salary;

    public SalesMan(String name) {
        this.name = name;
    }

}
