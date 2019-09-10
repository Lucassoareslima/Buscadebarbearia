package com.lucas.buscadebarbearia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.lucas.buscadebarbearia.R;
import com.lucas.buscadebarbearia.helper.ConfiguracaoFirebase;
import com.lucas.buscadebarbearia.helper.UsuarioFirebase;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button btnAcesso;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso, tipoUser;
    private LinearLayout linearTipoUser;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);


        inicializarComponente();
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        //deslogar usuario do sistema.
        //autenticacao.signOut();

        //verificação de usuario logado
        VerificarUsuarioLogado();

        tipoAcesso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //empresa
                    linearTipoUser.setVisibility(View.VISIBLE);

                }else{
                    //usuario
                    linearTipoUser.setVisibility(View.GONE);
                }
            }
        });

        btnAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                //verificação de campos

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){

                        //verificando estado Switch

                        if(tipoAcesso.isChecked()){
                            //cadastro
                            autenticacao.createUserWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        String tipoUsuario = getTipoUsuario();
                                        UsuarioFirebase.atualizarTipoUsuario(tipoUsuario);
                                        abrirTelaPrincipal(tipoUsuario);



                                    }else{
                                        String erroExcecao = "";
                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Digite uma senha mais forte!";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            erroExcecao = "Por favor,Digite um email valido.";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erroExcecao = "Esta conta ja foi cadastrada!";
                                        }catch (Exception e){
                                            erroExcecao = "Ao cadastrar usuário:" + e.getMessage();
                                            e.printStackTrace();
                                        }

                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Erro: " + erroExcecao,
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });


                        }else{//logar

                            autenticacao.signInWithEmailAndPassword(
                              email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(AutenticacaoActivity.this,
                                                    "Logado com Sucesso !",
                                                Toast.LENGTH_SHORT).show();

                                        String tipoUsuario = task.getResult().getUser().getDisplayName();

                                        abrirTelaPrincipal(tipoUsuario);

                                    }else{
                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Erro ao fazer o Login: " + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }

                    }else{
                        Toast.makeText(AutenticacaoActivity.this,
                                "Preencha a Senha!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(AutenticacaoActivity.this,
                            "Preencha o E-mail!",
                            Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void VerificarUsuarioLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual != null){

            String tipoUsuario = usuarioAtual.getDisplayName();
            abrirTelaPrincipal(tipoUsuario);
        }
    }


    private String getTipoUsuario(){
        return tipoUser.isChecked() ? "E" : "C";
    }

    private void abrirTelaPrincipal(String tipoUsuario){
        if(tipoUsuario.equals("E")){
            //empresa
            startActivity(new Intent(getApplicationContext(), BarbeariaActivity.class));

        }else{
            //Cliente
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void inicializarComponente(){
        campoEmail = findViewById(R.id.editCadEmail);
        campoSenha = findViewById(R.id.editCadPass);
        btnAcesso = findViewById(R.id.btnAcesso);
        tipoAcesso = findViewById(R.id.switchTipoUser);
        tipoUser = findViewById(R.id.switchTipoUser);
        linearTipoUser = findViewById(R.id.linearTipoUser);
    }



}
