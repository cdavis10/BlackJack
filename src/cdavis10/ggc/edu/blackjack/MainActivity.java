package cdavis10.ggc.edu.blackjack;
/*
 *I got my inspiration for the User interface here: http://www.edureka.co/blog/how-to-create-android-games-blackjack/ 
 *It helped give me a place to start.
 *Just some basic ideas on how to break down a blackjack app. but the CODE, 
 *as well as How to GET it to look like that is all mine
 * Sources:
	 * Images: http://svg-cards.sourceforge.net/ 
	 * animations: http://blingee.com/graphics/falling-coins
	 * sounds: http://www.pond5.com/ (paid for them!)
	 * 
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSeekBarChangeListener {
	int bet; // amount of bet
	int money; // Amount of money the player has left
	int pressCount = 0; // how many times the button was pressed
	int dealerScore = 0; // the dealer's score
	int playerScore = 0; // the player's score
	int tempCard; // stores the id of the card that is dealt temporarily so
					// methods can be called on it
	
	MediaPlayer mpAwww;
	
	public Card myCard = new Card(); // A Card.
	ArrayList<Integer> dealerHand = new ArrayList<Integer>(); // the cards
																// currently
																// dealt to the
																// dealer
	ArrayList<Integer> playerHand = new ArrayList<Integer>();// the cards
																// currently
																// dealt to the
																// player

	ImageView dealerCard1;
	ImageView dealerCard2;
	ImageView dealerCard3;
	ImageView dealerCard4;
	ImageView dealerCard5;

	/*
	 * the dealer's hand
	 */
	ImageView playerCard1;
	ImageView playerCard2;
	ImageView playerCard3;
	ImageView playerCard4;
	ImageView playerCard5;

	TextView myMoney;
	//TextView highScore; not using
	TextView txtDealerScore;
	TextView txtPlayerScore;
	TextView betText;

	Button placeBet;
	Button hit;
	Button stand;
	Button Surrender;
	Button newHand;

	SeekBar betBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dealerCard1 = (ImageView) findViewById(R.id.dealerCard1);
		dealerCard2 = (ImageView) findViewById(R.id.dealerCard2);
		dealerCard3 = (ImageView) findViewById(R.id.dealerCard3);
		dealerCard4 = (ImageView) findViewById(R.id.dealerCard4);
		dealerCard5 = (ImageView) findViewById(R.id.dealerCard5);

		playerCard1 = (ImageView) findViewById(R.id.playerCard1);
		playerCard2 = (ImageView) findViewById(R.id.playerCard2);
		playerCard3 = (ImageView) findViewById(R.id.playerCard3);
		playerCard4 = (ImageView) findViewById(R.id.playerCard4);
		playerCard5 = (ImageView) findViewById(R.id.playerCard5);

		myMoney = (TextView) findViewById(R.id.txtMoney);
		//highScore = (TextView) findViewById(R.id.txtHighScore);
		txtDealerScore = (TextView) findViewById(R.id.txtDealerScore);
		txtPlayerScore = (TextView) findViewById(R.id.txtPlayerScore);
		betText = (TextView) findViewById(R.id.txtBet);

		placeBet = (Button) findViewById(R.id.btnBet);
		hit = (Button) findViewById(R.id.btnHit);
		stand = (Button) findViewById(R.id.btnStand);
		Surrender = (Button) findViewById(R.id.btnSurrender);
		newHand = (Button) findViewById(R.id.btnNewHand);
		final Button exit = (Button) findViewById(R.id.btnExit);
		final Button rules = (Button) findViewById(R.id.btnRules);

		betBar = (SeekBar) findViewById(R.id.placeBet);

		findViewById(R.id.dealerLayout).setVisibility(View.VISIBLE);
		findViewById(R.id.playerLayout).setVisibility(View.VISIBLE);

		myMoney.setText("1500"); // the player's starting money

		money = Integer.parseInt(myMoney.getText().toString());
		betBar.setMax(money/5);// the maximum value a player can bet <= than
								// amount of money he has.
		betBar.incrementProgressBy(1);

		betBar.setOnSeekBarChangeListener(this);
		betBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			/*
			 * This determines how much money the player will bet.
			 */

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				betText.setText("" + (progress*5));
				bet = progress*5;// <-- sets the amount of bet = location on the
								// seek bar.
			}
		});
		placeBet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (bet < 5) {
					Toast.makeText(getApplicationContext(),
							"minimum bet is 5$", Toast.LENGTH_LONG).show();

				} else {
					/* subtracts bet from money and sets the text to reflect
					* balance
					*/
					money = money - bet;
					myMoney.setText("" + money);

					/*
					 * "turns off" the bet bar and bet button and "turn ON" the
					 * hit, stand, and surrender button.
					 */
					placeBet.setVisibility(View.INVISIBLE);
					betBar.setVisibility(View.INVISIBLE);
					hit.setVisibility(View.VISIBLE);
					stand.setVisibility(View.VISIBLE);
					Surrender.setVisibility(View.VISIBLE);

					// deals a card then sets that card as the image view then
					// calculates score based on card value.
					System.out.println("Dealer first card");
					tempCard = myCard.dealCard();
					dealerHand.add(tempCard);
					dealerCard1.setImageResource(tempCard);
					dealerScore = myCard.getScore(dealerHand);
					txtDealerScore.setText(" " + dealerScore);

					// same stuff but for each player card
					tempCard = myCard.dealCard();
					playerHand.add(tempCard);
					playerCard1.setImageResource(tempCard);

					System.out.println("Player's first two cards:");
					tempCard = myCard.dealCard();
					playerHand.add(tempCard);
					playerCard2.setImageResource(tempCard);
					playerScore = myCard.getScore(playerHand);
					txtPlayerScore.setText(" " + playerScore);

					if (myCard.hasBlackJack(playerHand)) {
						Toast.makeText(getApplicationContext(), "BLACKJACK!!",
								Toast.LENGTH_LONG).show();
						youWin();
						endHand();
					} else if (playerScore > 21) {
						Toast.makeText(getApplicationContext(),
								"BUST! You Loose!", Toast.LENGTH_LONG).show();
						youLoose();
						endHand();
					}

				}
			}

		});
		hit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Hit me!!");
				pressCount = pressCount + 1;
				tempCard = myCard.dealCard();
				switch (pressCount) {
				case 1:
					playerCard3.setImageResource(tempCard);

					break;
				case 2:
					playerCard4.setImageResource(tempCard);
					break;
				case 3:
					playerCard5.setImageResource(tempCard);
					break;
				default:
					break;
				}
				playerHand.add(tempCard);
				playerScore = myCard.getScore(playerHand);
				txtPlayerScore.setText(" " + playerScore);

				if (playerScore > 21) {
					Toast.makeText(getApplicationContext(), "BUST! You Loose!",
							Toast.LENGTH_LONG).show();
					endHand();
					youLoose();
				}
			}
		});
		stand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Dealer's turn");

				tempCard = myCard.dealCard();
				dealerHand.add(tempCard);
				dealerCard2.setImageResource(tempCard);
				dealerScore = myCard.getScore(dealerHand);
				System.out.println(" Dealer's score:" + dealerScore);
				txtDealerScore.setText(" " + dealerScore);

				if (myCard.hasBlackJack(dealerHand)) {
					Toast.makeText(getApplicationContext(),
							"Dealer has BLACKJACK!!  You loose!",
							Toast.LENGTH_LONG).show();
					endHand();
					youLoose();
				} else {
					int cardCount = 2; // <-- two cards dealt so far.

					while (dealerScore < 17 && dealerScore <= (playerScore + 1)) {
						System.out.println(" Card count: " + cardCount);
						tempCard = myCard.dealCard();
						switch (cardCount) {
						case 2:
							dealerCard3.setImageResource(tempCard);
							break;
						case 3:
							dealerCard4.setImageResource(tempCard);
							break;
						case 4:
							dealerCard5.setImageResource(tempCard);
							break;
						default:
							break;
						}
						cardCount++;
						dealerHand.add(tempCard);
						dealerScore = myCard.getScore(dealerHand);
						System.out.println(" Dealer's score:" + dealerScore);
						txtDealerScore.setText(" " + dealerScore);
					}
					endHand();
					if (dealerScore > 21) {
						Toast.makeText(getApplicationContext(),
								"Dealer bust!! Yoou win!!", Toast.LENGTH_LONG)
								.show();
						youWin();
					} else if (playerScore > dealerScore) {
						Toast.makeText(getApplicationContext(),
								"CONGRATULATIONS! You win!! ",
								Toast.LENGTH_LONG).show();
						youWin();
					} else if (playerScore == dealerScore) {
						Toast.makeText(getApplicationContext(), "Tie!",
								Toast.LENGTH_LONG).show();
						youLoose();
						money += bet;
					} else if (dealerScore > playerScore) {
						Toast.makeText(getApplicationContext(), "Dealer wins!",
								Toast.LENGTH_LONG).show();
						youLoose();
					}

				}

			}

		});
		Surrender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				money += (bet / 2);
				endHand();

			}
		});
		newHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameReset();

			}
		});
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 finish();
		         System.exit(0);
			}
		});
		rules.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, RulesActivity.class));
			}
		});
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void gameReset() {
		pressCount = 0;
		dealerScore = 0;
		playerScore = 0;
		dealerHand.clear();
		playerHand.clear();

		txtDealerScore.setText(" 0");
		txtPlayerScore.setText(" 0");

		dealerCard1.setImageResource(R.drawable.back_blue);
		dealerCard2.setImageResource(R.drawable.back_blue);
		dealerCard3.setImageResource(R.drawable.back_blue);
		dealerCard4.setImageResource(R.drawable.back_blue);
		dealerCard5.setImageResource(R.drawable.back_blue);

		playerCard1.setImageResource(R.drawable.back_red);
		playerCard2.setImageResource(R.drawable.back_red);
		playerCard3.setImageResource(R.drawable.back_red);
		playerCard4.setImageResource(R.drawable.back_red);
		playerCard5.setImageResource(R.drawable.back_red);

		betBar.setVisibility(View.VISIBLE);
		placeBet.setVisibility(View.VISIBLE);
		hit.setVisibility(View.INVISIBLE);
		stand.setVisibility(View.INVISIBLE);
		Surrender.setVisibility(View.INVISIBLE);
		newHand.setVisibility(View.INVISIBLE);
		hit.setClickable(true);
		stand.setClickable(true);
		Surrender.setClickable(true);

		myCard.newDeck();

	}

	public void youWin() {
		money += (bet * 2);
		myMoney.setText("" + money);
		
		startActivity(new Intent(MainActivity.this, YouWinActivity.class));
		
	}
	
	public void youLoose(){
		mpAwww = new MediaPlayer();
		mpAwww = MediaPlayer.create(this, R.raw.croud_reaction_awww);
		mpAwww.start();
		
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mpAwww.stop();
			}
		};
		Timer opening = new Timer();
		opening.schedule(task, 6000);
	}

	public void endHand() {
		hit.setVisibility(View.INVISIBLE);
		stand.setVisibility(View.INVISIBLE);
		Surrender.setVisibility(View.INVISIBLE);
		newHand.setVisibility(View.VISIBLE);
	}

}
