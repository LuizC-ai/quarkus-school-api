package com.example.school.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public class ModeloBase implements Modelo {


    @Column( name = "data_criacao" )
    @Setter( AccessLevel.NONE )
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Setter( AccessLevel.NONE )
    Instant dataUltimaModificacao;

    @Version
    @Setter( AccessLevel.NONE )
    Long versao;

    @Setter( AccessLevel.NONE )
    @Column( unique = true )
    String identificador;

    @Column( name = "ultima_atualizacao" )
    private LocalDateTime ultimaAtualizacao;

    boolean apagado;

    boolean ativo;

    @PrePersist
    protected void antesDePersistir( ) {
        if ( null == identificador ) {
            gerarIdentificador( );
    }
        this.dataCriacao =LocalDateTime.now();
        this.ultimaAtualizacao =LocalDateTime.now();
}


    private void gerarIdentificador( ) {
        identificador = UUID.randomUUID( ).toString( ).substring( 0, getIdentificadorQntCaracteres( ) );
    }

    @PreUpdate
    private void antesDeAtualizar() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    protected int getIdentificadorQntCaracteres( ) {
        return 8;
    }

    @Override
    public int hashCode( ) {
        return Objects.hash( dataCriacao, dataUltimaModificacao, identificador, apagado, ativo );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof ModeloBase that) ) return false;
        return apagado == that.apagado && ativo == that.ativo && Objects.equals( dataCriacao, that.dataCriacao ) && Objects.equals( dataUltimaModificacao, that.dataUltimaModificacao ) && Objects.equals( identificador, that.identificador );
    }
}
