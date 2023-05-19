package br.com.alura.agenda.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String CHAVE_ALUNO = "aluno";
    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private Aluno aluno;
    private final AlunoDao alunoDao = new AlunoDao();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.strongGreen));
        }

        EditText campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        EditText campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        EditText campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);

        carregaAluno(campoEmail, campoNome, campoTelefone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_alunos_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){
            EditText campoNome = findViewById(R.id.activity_formulario_aluno_nome);
            EditText campoEmail = findViewById(R.id.activity_formulario_aluno_email);
            EditText campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
            preencheALuno_FinalizaFormulario(campoNome, campoEmail, campoTelefone);
        }
        return super.onOptionsItemSelected(item);
    }

    private void preencheALuno_FinalizaFormulario(EditText campoNome, EditText campoEmail, EditText campoTelefone) {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setTelefone(telefone);

        finalizaFormulario();
    }

    private void finalizaFormulario() {
        if(aluno.temIdValido()){
            alunoDao.edita(aluno);
        }else{
            alunoDao.salva(aluno);
        }
        finish();
    }

    private void carregaAluno(EditText campoEmail, EditText campoNome, EditText campoTelefone) {
        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_ALUNO)){
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) intent.getSerializableExtra(CHAVE_ALUNO);
            preencheAluno(campoEmail, campoNome, campoTelefone);
        }else{
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheAluno(EditText campoEmail, EditText campoNome, EditText campoTelefone) {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        campoTelefone.setText(aluno.getTelefone());
    }
}