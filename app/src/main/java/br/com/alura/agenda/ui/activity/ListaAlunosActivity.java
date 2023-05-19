package br.com.alura.agenda.ui.activity;

import static br.com.alura.agenda.dao.AlunoDao.remove;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String CHAVE_ALUNO = "aluno";
    private static ArrayAdapter<Aluno> adapter;
    private final AlunoDao alunoDao = new AlunoDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_alunos);

        setTitle("Lista de alunos");

        alunoDao.salva(new Aluno("Pedro", "2133212312", "pedro@gmail.com"));
        alunoDao.salva(new Aluno("Maria", "2133212312", "maria@gmail.com"));

        FloatingActionButton fab = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);

        fab.setOnClickListener((View view) -> startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class)));

        configuraLista();

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.strongGreen));
        }
        invalidateOptionsMenu();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            remove(alunoEscolhido);
            atualizaLista();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaLista();
    }

    private void atualizaLista() {
        adapter.clear();
        adapter.addAll(alunoDao.todos());
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);

        configuraAdapter(listaDeAlunos);

        configuraListenerDeCliquePorItem(listaDeAlunos);

        registerForContextMenu(listaDeAlunos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener((AdapterView<?> adapterView, View view, int posicao, long l) -> {abreFormularioModoEditaAluno(adapterView, posicao);});
    }

    private void abreFormularioModoEditaAluno(AdapterView<?> adapterView, int posicao) {
        Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
        vaiParaFormulario.putExtra(CHAVE_ALUNO, alunoEscolhido);
        startActivity(vaiParaFormulario);
    }

    private void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDeAlunos.setAdapter(adapter);
    }
}
