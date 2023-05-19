package br.com.alura.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Aluno;

public class AlunoDao {
    private static final List<Aluno> listaDeAlunos = new ArrayList<>();
    private static int contadordeIds = 1;

    public void salva(Aluno aluno) {
        listaDeAlunos.add(aluno);
        aluno.setId(contadordeIds);
        contadordeIds++;
    }

    public void edita(Aluno aluno){
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        boolean encontrouAluno = alunoEncontrado != null;
        if(encontrouAluno){
            int i = listaDeAlunos.indexOf(alunoEncontrado);
            listaDeAlunos.set(i, aluno);
        }
    }

    @Nullable
    private static Aluno buscaAlunoPeloId(Aluno aluno) {
        for (Aluno a: listaDeAlunos) {
            if(a.getId() == aluno.getId()){
                return a;
            }
        }
        return null;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(listaDeAlunos);
    }

    public static void remove(Aluno aluno) {
        Aluno alunoPeloId = buscaAlunoPeloId(aluno);
        if(alunoPeloId != null){
            listaDeAlunos.remove(alunoPeloId);
        }
    }
}
