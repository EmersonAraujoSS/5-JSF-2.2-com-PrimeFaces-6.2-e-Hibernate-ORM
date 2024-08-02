package br.com.avancard.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class TelefoneUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String numero;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private UsuarioPessoa usuarioPessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }

    public UsuarioPessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }
}
