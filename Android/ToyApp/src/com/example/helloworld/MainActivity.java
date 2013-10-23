package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity{

	private RelativeLayout _layout = null;
	private Button _buttonBold = null;
	private Button _buttonItalic = null;
	private Button _buttonUnderline = null;
	private EditText _editingTextFromUser = null;
	private TextView _rendering = null;
	
	private OnClickListener _clickListener = new OnClickListener() {
		@Override
		public void onClick(View parView) {
			/*			
			switch(parView.getId()) {
	
			case R.id.buttonBold: 							//TODO TODO
				break;			
			case R.id.buttonItalic:
				break;	
			case R.id.buttonUnderline:
				break;		
			} */
			
		}
	};
	
	private TextWatcher _textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			_rendering.setText(arg0);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
   	
		// On récupère notre layout par désérialisation. La méthode inflate retourne un View
	    // C'est pourquoi on caste (on convertit) le retour de la méthode avec le vrai type de notre layout, c'est-à-dire RelativeLayout
		_layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_main, null);
	    
		
		// Button Bold
	    _buttonBold = (Button) _layout.findViewById(R.id.buttonBold);
	    _buttonBold.setOnClickListener(_clickListener);
	    
		// Button Italic
	    _buttonItalic = (Button) _layout.findViewById(R.id.buttonItalic);
	    _buttonItalic.setOnClickListener(_clickListener);	    
	    
		// Button Underline
	    _buttonUnderline = (Button) _layout.findViewById(R.id.buttonUnderline);
	    _buttonUnderline.setText(R.string.underline);
	    _buttonUnderline.setOnClickListener(_clickListener);	    
	    
	    // Edit text from user
	    _editingTextFromUser = (EditText) _layout.findViewById(R.id.editTextFromUser);
	    _editingTextFromUser.addTextChangedListener(_textWatcher);
	    
	    //Rendering 
	    _rendering = (TextView) _layout.findViewById(R.id.rendering);
	    _rendering.setText("PLOUF");
	    
	    
	    
	    setContentView(_layout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
