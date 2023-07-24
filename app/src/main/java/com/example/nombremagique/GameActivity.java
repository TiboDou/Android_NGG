package com.example.nombremagique;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean deuxChiffres, troisChiffres, quatreChiffres;

    Random r = new Random();
    int random;
    int chancesRestantes = 10;

    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewHint = findViewById(R.id.textViewHint);
        textViewRight = findViewById(R.id.textViewRight);
        textViewLast = findViewById(R.id.textViewLast);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        editTextGuess = findViewById(R.id.editTextGuess);

        deuxChiffres = getIntent().getBooleanExtra("deux", false);
        troisChiffres = getIntent().getBooleanExtra("trois", false);
        quatreChiffres = getIntent().getBooleanExtra("quatre", false);

        if (deuxChiffres) {
            random = r.nextInt(90) + 10;
        }
        if (troisChiffres) {
            random = r.nextInt(900) + 100;
        }
        if (quatreChiffres) {
            random = r.nextInt(9000) + 1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals("")) {
                    Toast.makeText(GameActivity.this, "UN NOMBRE SVP", Toast.LENGTH_LONG).show();
                }
                else {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts ++;
                    chancesRestantes --;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Votre dernière tentative : " + guess);
                    textViewRight.setText("Votre nombre de tentatives restantes : " + chancesRestantes);

                    if (random == userGuess) {
                        /* Sources pour Dialogs :
                         * https://developer.android.com/develop/ui/views/components/dialogs
                         *   */

                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Nombre Magique");
                        builder.setCancelable(false);
                        builder.setMessage("Bravo, vous avez trouvé le nombre " + random + " en " + userAttempts + " tentatives. \n\n " +
                                "Vos tentatives : " + guessesList + "\n\n Voulez-vous rejouer ?");
                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    if (random < userGuess) {
                        textViewHint.setText("C'est moins");
                    }
                    if (random > userGuess) {
                        textViewHint.setText("C'est plus");
                    }
                    if (chancesRestantes == 0) {
                        /* Sources pour Dialogs :
                         * https://developer.android.com/develop/ui/views/components/dialogs
                         *   */

                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Nombre Magique");
                        builder.setCancelable(false);
                        builder.setMessage("Pas bravo, vous n'avez pas trouvé le nombre " + random + " malgré vos 10 tentatives. \n\n " +
                                "Vos tentatives : " + guessesList + "\n\n Voulez-vous rejouer ?");
                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }

                    editTextGuess.setText("");
                }
            }
        });
    }
}