package Albastro.turnbasedgame;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtMonsName, txtHeroName, txtMonsHP, txtHeroHP, txtHeroDPT, txtMana , txtCombatLog ,txtMonsDPT;
    Button btnNextTurn;
    ImageButton ability1,ability2;

    //Hero Class
    int heroHP =        2000;
    int heroMinDPT =    100;
    int heroMaxDPT =    110;
    int heroMana =       90;
    int attackIncrease = 0;
    String heroName  =  "Knight";

    //Monster Class
    int monsHP =        1700;
    int monsMinDPT =    100;
    int monsMaxDPT =    140;
    String monsName  =  "Demon";

    int turnNumber = 1;

    int burnEffect = 0;
    int buttoncounter = 0;
    int buttoncounter2 = 0;
    int attackBuff = 0;




    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        btnNextTurn = findViewById(R.id.btnNextTurn);

        txtMonsName = findViewById(R.id.txtEnemy_name);
        txtHeroName = findViewById(R.id.txtPlayer_name);
        txtMonsHP = findViewById(R.id.txtEnemy_hp);
        txtHeroHP = findViewById(R.id.txtPlayer_hp);
        txtHeroDPT = findViewById(R.id.txtPlayerDPT);
        txtMonsDPT = findViewById(R.id.txtEnemyDPT);
        txtMana = findViewById(R.id.manaPoints);

        txtHeroName.setText(heroName);
        txtMonsName.setText(monsName);

        txtHeroHP.setText(String.valueOf(heroHP));
        txtMana.setText(String.valueOf(heroMana));
        txtMonsHP.setText(String.valueOf(monsHP));

        txtHeroDPT.setText(heroMinDPT + " - " + heroMaxDPT);
        txtMonsDPT.setText(monsMinDPT + " - " + monsMaxDPT);

        ability1 = findViewById(R.id.Ability1);
        ability2 = findViewById(R.id.Ability2);


        btnNextTurn.setOnClickListener(this);
        ability1.setOnClickListener(this);
        ability2.setOnClickListener(this);
    }
    private void reduceCoolDown() {
        if (buttoncounter > 0) {
            buttoncounter -= 1;
        }
        if (buttoncounter2 > 0) {
            buttoncounter2 -= 1;
        }
    }


    @Override
    public void onClick(View v) {

        Random randomizer = new Random();

        btnNextTurn = findViewById(R.id.btnNextTurn);
        txtMonsHP = findViewById(R.id.txtEnemy_hp);
        txtHeroHP = findViewById(R.id.txtPlayer_hp);
        txtCombatLog = findViewById(R.id.txtTurnLog);

        txtMonsHP.setText(String.valueOf(monsHP));
        txtHeroHP.setText(String.valueOf(heroHP));
        txtMana.setText(String.valueOf(heroMana));

        if(turnNumber % 2 == 1){
            ability1.setEnabled(false);
            ability2.setEnabled(false);

        }
        else if (turnNumber %2 == 0) {
            if(buttoncounter > 0){
                ability1.setEnabled(false);
            }
            else if(buttoncounter == 0 ){
                ability1.setEnabled(true);
            }
            if(buttoncounter2 > 0){
                ability2.setEnabled(false);
            }
            else if(buttoncounter2 == 0 ){
                ability2.setEnabled(true);
            }

        }

        if (attackBuff == 0 ) {
            attackIncrease = 0;
        }

        if (turnNumber %2 != 1 && burnEffect > 0) {
            monsHP -= 150;
            burnEffect  -= 1;

        }

        int heroDPT = randomizer.nextInt(heroMaxDPT - heroMinDPT) + heroMinDPT + attackIncrease;
        int monsDPT = randomizer.nextInt(monsMaxDPT - monsMinDPT) + monsMinDPT;

        switch(v.getId()) {
            case R.id.Ability2:
                if (heroMana > 30 ) {
                    buttoncounter2 = 7;
                    heroMana    -= 30;
                    attackIncrease = 50;
                    attackBuff = 2;
                    turnNumber++;
                    txtMonsHP.setText(String.valueOf(monsHP));
                    btnNextTurn.setText("Next Turn (" + turnNumber + ")");
                    txtCombatLog.setText("The Knight " + " used a buff! You will be \ndealing additional 50 damage for 2 turns!");
                }
                else {
                    txtCombatLog.setText("Mana insufficient");
                }
                if(monsHP < 0){ //even
                    txtCombatLog.setText("The Knight " +" dealt "+ heroDPT + " damage to the enemy. The player is victorious!");
                }
                break;

                case R.id.Ability1:
                if (heroMana > 30 ) {
                    buttoncounter = 10;
                    heroMana    -= 30;
                    burnEffect= 3;
                    turnNumber++;
                    txtMonsHP.setText(String.valueOf(monsHP));
                    btnNextTurn.setText("Next Turn (" + turnNumber + ")");
                    txtCombatLog.setText("The Knight " + " burned the enemy! You will be \ndealing a continous damage of 150 every turn for 3 turns!");
                }
                else {
                    txtCombatLog.setText("Mana insufficient");
                }
                if(monsHP < 0){ //even
                    txtCombatLog.setText("The Knight " +" dealt "+ heroDPT + " damage to the enemy. The player is victorious!");
                }
                break;

            case R.id.btnNextTurn:

                        if (turnNumber % 2 == 1) {

                            monsHP = monsHP - heroDPT;
                            turnNumber++;

                            txtCombatLog.setText("The Player dealt " + heroDPT + " damage to the Enemy");

                            txtMonsHP.setText(String.valueOf(monsHP));

                            btnNextTurn.setText("Enemy's Turn (" + turnNumber + ")");

                            if (monsHP < 0) {
                                txtCombatLog.setText("The Player dealt " + heroDPT + " damage to the Enemy. \nThe Player was Victorious!");
                                heroHP = 2000;
                                monsHP = 1700;
                                turnNumber = 1;
                                btnNextTurn.setText("Reset Game");
                            } // end of the resetting condition
                        } // end of player's turn condition

                        else if (turnNumber % 2 != 1) {
                            heroHP = heroHP - monsDPT;
                            turnNumber++;
                            txtCombatLog.setText("The Monster dealt " + monsDPT + " damage to the Player");
                            txtHeroHP.setText(String.valueOf(heroHP));
                            btnNextTurn.setText("Player's Turn (" + turnNumber + ")");
                            if (heroHP < 0) {
                                txtCombatLog.setText("The Monster dealt " + monsDPT + " damage to the Player. The Player Died");
                                heroHP = 2000;
                                monsHP = 1700;
                                turnNumber = 1;
                                btnNextTurn.setText("Reset Game");
                            }// end of the resetting condition
                        } // end of the monsters turn condition
                          // switch breaker for the button function. DO NOT EDIT
                    Log.d(TAG, "buttonCounter1: " + buttoncounter);
                    Log.d(TAG, "buttonCounter2: " + buttoncounter2);
                    reduceCoolDown();
                break;
                }
        }
    }