package au.edu.unsw.cse.view.processes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
 
public class ProcessRatingCommentsActivity extends Activity {
 
  private final int RATING_SUBMITTED=1;

	
  private RatingBar ratingBar;
  private TextView txtRatingValue;
  private Button btnSubmit;
  private EditText comments;
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.ratings_and_comments_layout);
 
	addListenerOnRatingBar();
	addListenerOnButton();
	addListenerEditText();
 
  }
 
  public void addListenerOnRatingBar() {
 
	ratingBar = (RatingBar) findViewById(R.id.ratingBar);
	txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
	comments = (EditText) findViewById(R.id.comments);

 
	//if rating value is changed,
	//display the current rating value in the result (textview) automatically
	ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
		public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
 
			txtRatingValue.setText(String.valueOf(rating));
 
		}
	});
  }
  
  public void addListenerEditText(){
	  comments.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          Toast.makeText(ProcessRatingCommentsActivity.this, comments.getText(), Toast.LENGTH_SHORT).show();
		          return true;
		        }
		        return false;
		    }
		});
  }
 
  public void addListenerOnButton() {
 
	ratingBar = (RatingBar) findViewById(R.id.ratingBar);
	btnSubmit = (Button) findViewById(R.id.btnSubmit);
	Bundle b = this.getIntent().getExtras();
	final int processID = b.getInt("pid");


 
	//if click on me, then display the current rating value.
	btnSubmit.setOnClickListener(new OnClickListener() {
 
		@Override
		public void onClick(View v) {
 
			Intent i = new Intent();
	        i.putExtra("rating", ratingBar.getRating());
	        i.putExtra("pid", processID);
	        i.putExtra("comments", comments.getText().toString());

			Log.i("ProcessRatingCommentsActivity", "rating = " + ratingBar.getRating());
			Log.i("ProcessRatingCommentsActivity", "pid = " + processID);
			Log.i("ProcessRatingCommentsActivity", "comments = " +comments.getText().toString());



	        setResult(RATING_SUBMITTED, i);
	        finish();
 
		}
 
	});
 
  }
}

