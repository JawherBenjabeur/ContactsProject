package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;



import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Contacting_Activity2 extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layoutNaviguer,layoutsearch,layoutAppeler;
    EditText  _txtName, _txtAddress, _txtNum1 , _txtNum2, _txtEntreprise, _txtsearch;
    ImageButton _btnsearch ;
    Button _btnPrevious, _btnNext ;
    Button _btnAppeler;
    Button _btnAjout, _btnMiseAjour, _btnDelete;
    Button  _btnAnnuler, _btnEnreg;

    int op = 0;
    String x ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacting_2);

        layoutNaviguer = (LinearLayout) findViewById(R.id.layNavig);
        layoutsearch = (LinearLayout) findViewById(R.id.laysearch);
        layoutAppeler = (LinearLayout) findViewById(R.id.layAppeler) ;


        _txtsearch = (EditText)findViewById(R.id.txtsearch);

        _txtName = (EditText)findViewById(R.id.txtName);
        _txtAddress = (EditText)findViewById(R.id.txtAddress);
        _txtNum1 = (EditText)findViewById(R.id.txtNum1);
        _txtNum2 = (EditText)findViewById(R.id.txtNum2);
        _txtEntreprise = (EditText)findViewById(R.id.txtEntreprise);

        _btnPrevious = (Button)findViewById(R.id.btnPrevious);
        _btnNext = (Button)findViewById(R.id.btnNext);
        _btnAppeler= (Button)findViewById(R.id.btnAppeler);

        _btnAjout = (Button)findViewById(R.id.btnAjout);
        _btnMiseAjour = (Button)findViewById(R.id.btnMiseAjour);
        _btnDelete = (Button)findViewById(R.id.btnDelete);

        _btnAnnuler = (Button)findViewById(R.id.btnAnnuler);
        _btnEnreg = (Button)findViewById(R.id.btnEnreg);

        _btnsearch = (ImageButton)findViewById(R.id.btnsearch);

        //creation d'une connexion vers la base de données
        db = openOrCreateDatabase("Contacts",MODE_PRIVATE,null);
        //création de la table contacts
        db.execSQL("CREATE TABLE IF NOT EXISTS Contacts(id Integer primary key autoincrement, nom VARCHAR, adresse VARCHAR, tel1 VARCHAR,tel2 VARCHAR, entreprise VARCHAR);");

        layoutNaviguer.setVisibility(View.INVISIBLE);
        _btnEnreg.setVisibility(View.INVISIBLE);
        _btnAnnuler.setVisibility(View.INVISIBLE);
        layoutAppeler.setVisibility(View.INVISIBLE);

        _btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur = db.rawQuery("select * from Contacts where (nom like ?) OR (entreprise like ?) OR (tel1 like ?) OR (tel2 like ?)",new String[]{"%" + _txtsearch.getText().toString() + "%" , "%" + _txtsearch.getText().toString() + "%", "%" + _txtsearch.getText().toString() + "%","%" + _txtsearch.getText().toString() + "%"});
                try {

                    cur.moveToFirst();
                    _txtName.setText(cur.getString(1));
                    _txtAddress.setText(cur.getString(2));
                    _txtNum1.setText(cur.getString(3));
                    _txtNum2.setText(cur.getString(4));
                    _txtEntreprise.setText(cur.getString(5));
                    if(cur.getCount()==1){
                        layoutNaviguer.setVisibility(View.INVISIBLE);

                    }else {
                        layoutNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                    callVisibility();


                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat",Toast.LENGTH_SHORT).show();
                    _txtName.setText("");
                    _txtAddress.setText("");
                    _txtNum1.setText("");
                    _txtNum2.setText("");
                    _txtEntreprise.setText("");
                    layoutNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });
        
        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();

                    _txtName.setText(cur.getString(1));
                    _txtAddress.setText(cur.getString(2));
                    _txtNum1.setText(cur.getString(3));
                    _txtNum2.setText(cur.getString(4));
                    _txtEntreprise.setText(cur.getString(5));
                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }
                    callVisibility();


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();

                    _txtName.setText(cur.getString(1));
                    _txtAddress.setText(cur.getString(2));
                    _txtNum1.setText(cur.getString(3));
                    _txtNum2.setText(cur.getString(4));
                    _txtEntreprise.setText(cur.getString(5));
                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }

                    callVisibility();


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;

                _txtName.setText("");
                _txtAddress.setText("");
                _txtNum1.setText("");
                _txtNum2.setText("");
                _txtEntreprise.setText("");
                _btnEnreg.setVisibility(View.VISIBLE);
                _btnAnnuler.setVisibility(View.VISIBLE);
                _btnMiseAjour.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAjout.setEnabled(false);
                layoutNaviguer.setVisibility(View.INVISIBLE);
                layoutsearch.setVisibility(View.INVISIBLE);
                layoutAppeler.setVisibility(View.INVISIBLE);
            }
        });

        _btnMiseAjour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tester si les champs ne sont pas vides
                try {
                    x = cur.getString(0);
                    op = 2;

                    _btnEnreg.setVisibility(View.VISIBLE);
                    _btnAnnuler.setVisibility(View.VISIBLE);

                    _btnDelete.setVisibility(View.INVISIBLE);
                    _btnMiseAjour.setEnabled(false);
                    _btnAjout.setVisibility(View.INVISIBLE);

                    layoutNaviguer.setVisibility(View.INVISIBLE);
                    layoutsearch.setVisibility(View.INVISIBLE);
                    layoutAppeler.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Sélectionnez un contact puis appyuer sur le bouton de modification",Toast.LENGTH_SHORT).show();
                }

            }
        });

        _btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (op == 1){
                    // insertion
                    db.execSQL("insert into Contacts (nom,adresse,tel1,tel2,entreprise) values (?,?,?,?,?);", new String[] {_txtName.getText().toString() , _txtAddress.getText().toString(),_txtNum1.getText().toString(),_txtNum2.getText().toString(),_txtEntreprise.getText().toString()});
                } else if (op == 2) {
                    // Mise à jour
                        db.execSQL("update Contacts set  nom=?, adresse=?, tel1=?, tel2=?,entreprise=? where id=?;", new String[] { _txtName.getText().toString() , _txtAddress.getText().toString(),_txtNum1.getText().toString(),_txtNum2.getText().toString(),_txtEntreprise.getText().toString(),x});
                }

                _btnEnreg.setVisibility(View.INVISIBLE);
                _btnAnnuler.setVisibility(View.INVISIBLE);
                _btnMiseAjour.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAjout.setVisibility(View.VISIBLE);
                _btnAjout.setEnabled(true);
                _btnMiseAjour.setEnabled(true);
                _btnsearch.performClick();
                layoutsearch.setVisibility(View.VISIBLE);
            }
        });
        _btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 0;

                _btnEnreg.setVisibility(View.INVISIBLE);
                _btnAnnuler.setVisibility(View.INVISIBLE);
                _btnMiseAjour.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAjout.setVisibility(View.VISIBLE);
                _btnAjout.setEnabled(true);
                _btnMiseAjour.setEnabled(true);

                layoutsearch.setVisibility(View.VISIBLE);
            }
        });


        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    x=  cur.getString(0);
                    AlertDialog dial = MesOptions();
                    dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Sélectionner un compte puis appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        _btnAppeler.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog dial = MyOptions();
                    dial.show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Sélectionner",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce compte?")

                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from Contacts where id=?;",new String[] {cur.getString(0)});

                        _txtName.setText("");
                        _txtAddress.setText("");
                        _txtNum1.setText("");
                        _txtNum2.setText("");
                        _txtEntreprise.setText("");
                        layoutNaviguer.setVisibility(View.INVISIBLE);
                        cur.close();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;
    }

    private void callVisibility(){
        if (((_txtNum1.getText().length() ==0) && (_txtNum2.getText().length() == 0)) ){

            layoutAppeler.setVisibility(View.INVISIBLE);

        } else  {
            layoutAppeler.setVisibility(View.VISIBLE);

        }

    }

    private AlertDialog MyOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("confirmation")
                .setMessage("Séléctionnez le numéro que vous allez appeler")

                .setPositiveButton("tel1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //selectionnez le num 1 et appeller
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+_txtNum1.getText().toString().trim()));
                        startActivity(callIntent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("tel2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //selectionner le tel2 et appllez
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+_txtNum2.getText().toString().trim()));
                        startActivity(callIntent);
                        dialogInterface.dismiss();
                    }

                })
                .create();
        return MiDia;
    }


}