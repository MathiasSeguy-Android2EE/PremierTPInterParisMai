package com.android2ee.formation.mai.mmxiii.premiertp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to be the first app
 */
public class MainActivity extends Activity implements HumanAdapterCallBack {
	/**
	 * Edt Message
	 */
	EditText edtMessage;
	/**
	 * button Add
	 */
	Button btnAdd;
	/**
	 * The listView
	 */
	ListView lstMessage;

	/**
	 * The items to display
	 */
	List<Human> items;
	/**
	 * The arrayAdpter that is bound to the ListView
	 */
	HumanAdapter arrayAdapter;
	/**
	 * The AlertDialog constant
	 */
	private final static int ALERT_DIALOG1 = 1000001;

	/******************************************************************************************/
	/** Managing life cycle **************************************************************************/
	/******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Les findViewById
		instantiateView();
		// ListView initialization
		initializeListView();
		// Add listener on ListView and button
		addListeners();
	}

	/******************************************************************************************/
	/** CreateDialog **************************************************************************/
	/******************************************************************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case ALERT_DIALOG1:
			dialog = createFirstDialog();
			break;
		default:
			break;
		}
		return dialog;
	}

	private Dialog createFirstDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You should externalyze that string");
		builder.setTitle("Title (hard coded)");
		builder.setPositiveButton("Yes", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogDismiss(Activity.RESULT_OK);

			}
		});
		builder.setNegativeButton("No", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogDismiss(RESULT_NOK);

			}
		});
		builder.setNeutralButton("?o?", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogDismiss(Activity.RESULT_CANCELED);

			}
		});
		return builder.create();
	}

	private static final int RESULT_NOK = -10010010;

	/**
	 * When the dialog closes this method is called
	 * 
	 * @param state
	 */
	private void dialogDismiss(int state) {
		String result;
		switch (state) {
		case RESULT_NOK:
			result="nok";
			break;
		case RESULT_OK:
			result="ok";
			break;
		case RESULT_CANCELED:
			result="cancel";
			break;
		default:
			result="unde";
			break;
		}
		Toast.makeText(this, "The result is:! "+result, Toast.LENGTH_SHORT).show();
	}

	/******************************************************************************************/
	/** Instantiate Activity **************************************************************************/
	/******************************************************************************************/

	/**
	 * 
	 */
	private void initializeListView() {
		// Instanciate the listView
		items = new ArrayList<Human>();
		Human current;
		for (int i = 0; i < 1000; i++) {
			current = new Human(" message " + i);
			items.add(current);
		}
		arrayAdapter = new HumanAdapter(this, items);
		
		lstMessage.setAdapter(arrayAdapter);
	}

	/**
	 * 
	 */
	private void addListeners() {
		lstMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				itemSelected(position);
			}
		});
		// Add listener
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnAddClicked();
			}
		});
	}

	/**
	 * 
	 */
	private void instantiateView() {
		edtMessage = (EditText) findViewById(R.id.edt_message);
		btnAdd = (Button) findViewById(R.id.btn_add);
		lstMessage = (ListView) findViewById(R.id.lsv_messages);
		Log.e(this.getClass().getSimpleName(),"My log message");
	}

	/******************************************************************************************/
	/** Managing selection **************************************************************************/
	/******************************************************************************************/

	/* (non-Javadoc)
	 * @see com.android2ee.formation.mai.mmxiii.premiertp.HumanAdapterCallBack#itemSelected(int)
	 */
	@Override
	public void itemSelected(int position) {
		Human human = arrayAdapter.getItem(position);
		edtMessage.setText(human.getMessage());
	}

	/**
	 * Called when a click is done on the btnAdd
	 */
	private void btnAddClicked() {
		// Read EditText
		String str = edtMessage.getText().toString();
		Human toto = new Human(str);
		// Add the string to the txvMessages
		arrayAdapter.add(toto);
		// Or
		// items.add(str);
		// arrayAdapter.notifyDataSetChanged();
		// and flush the editText
		edtMessage.setText("");
		showDialog(ALERT_DIALOG1);
	}
}
