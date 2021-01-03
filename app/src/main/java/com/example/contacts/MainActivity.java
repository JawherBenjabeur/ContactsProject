package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.contacts.HelpMe.sha256;

public class MainActivity extends AppCompatActivity {

    EditText _txtLogin, _txtPassword ;
    Button _btnConnection;
    SQLiteDatabase db ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txtLogin = (EditText) findViewById(R.id.txtLogin);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);
        _btnConnection = (Button) findViewById(R.id.btnConnection);

        //creation de la base de données ou connexion
        db = openOrCreateDatabase("Contacts",MODE_PRIVATE, null);
        //creation de la table "users"
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS (login varchar primary key, password varchar);");
        // si la table "users" est vide alors il faut ajouter l'utlisateur admin avec mot de pass "0000"
        SQLiteStatement s = db.compileStatement("select count(*) from users;");
        long c = s.simpleQueryForLong();
        if (c==0){
            db.execSQL("insert into users ( login,password) values (?,?)", new String[] {"admin", sha256("0000")});
        }
        _btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strLogin = _txtLogin.getText().toString();
                String strPwd = _txtPassword.getText().toString();
                //crée un curseur pour récupérer le résultat de la rrquete select
                Cursor cur = db.rawQuery("select password from users where login =?",new String[]{strLogin});
                try {
                    cur.moveToFirst();
                    String p = cur.getString(0);
                    if (p.equals(sha256(strPwd))){
                        Toast.makeText(getApplicationContext(),"Bienvenue"+" "+ strLogin,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),Contacting_Activity2.class);
                        startActivity(i);

                    }else{
                        _txtLogin.setText("");
                        _txtPassword.setText("");
                        Toast.makeText(getApplicationContext(),"UTILISATEUR Inexistant",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}