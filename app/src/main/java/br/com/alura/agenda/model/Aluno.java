package br.com.alura.agenda.model;

import android.widget.EditText;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {
    private String nome;
    private String email;
    private String telefone;
    private int idAluno = 0;

    public Aluno(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Aluno() {

    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId(int contadordeIds) {
        this.idAluno = contadordeIds;
    }

    public int getId(){
        return this.idAluno;
    }

    public boolean temIdValido() {
        return idAluno > 0;
    }
}
