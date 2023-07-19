package org.ufrpe.inovagovlab.decisoestce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

class MeuTextoPK implements Serializable{
    private Usuario usuario;
    private LocalDateTime dataDeCriacao;

    public MeuTextoPK(Usuario usuario, LocalDateTime dataDeCriacao) {
        this.usuario = usuario;
        this.dataDeCriacao = dataDeCriacao;
    }
}
@Entity
@Data
@IdClass(MeuTextoPK.class)
public class MeuTexto {

    @Column
    private String meuTextoOriginal;
    @Column
    private String meuTextoSimplificado;
    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable = false)
    @Id
    private Usuario usuario;
    @Id
    private LocalDateTime dataDeCriacao;

}
