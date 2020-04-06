package br.com.localhost.comercialapi.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity()
@Table(name="teste")
public class Teste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 100)
    private String nome;

    @NotNull
    @Min(0)
    private BigDecimal valor;

    public void setId(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setValor(BigDecimal valor){
        this.valor = valor;
    }

    public BigDecimal getValor(){
        return this.valor;
    }

}
