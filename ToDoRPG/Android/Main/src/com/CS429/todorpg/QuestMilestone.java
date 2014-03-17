package com.CS429.todorpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuestMilestone extends Activity {

	TextView countView;
	EditText milestone_step;
	int count;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.milestone_popup);
		ActivitySizeHandler();
		Initialize();
	}

	protected void Initialize() {
		count = getIntent().getIntExtra("count", -1);
		countView = (TextView) findViewById(R.id.mCount);
		countView.setText("Milestone " + count);

		milestone_step = (EditText) findViewById(R.id.mEdit);

		findViewById(R.id.milestone_cancel).setOnClickListener(chooseOption);
		findViewById(R.id.milestone_next).setOnClickListener(chooseOption);
		findViewById(R.id.milestone_upload).setOnClickListener(chooseOption);
	}

	private void ActivitySizeHandler() {
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		this.getWindow().setAttributes(params);
	}

	private boolean NotComplete(int len) {
		boolean result = (len == 0);
		if (result) {
			AlertDialog NullCheckBox = GetNullDataHandleDialog();
			NullCheckBox.show();
		}
		return result;
	}

	Button.OnClickListener chooseOption = new Button.OnClickListener() {
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.milestone_cancel:
				AlertDialog finishBox = GetFinishStepOption();
				finishBox.show();
				break;

			case R.id.milestone_next:
				if (NotComplete(milestone_step.getText().toString().trim().length())) {
					return;
				}
				MoveToNextStep();
				finish();
				break;

			case R.id.milestone_upload:
				if (NotComplete(milestone_step.getText().toString().trim().length())) {
					return;
				}

				QuestCreation.milestone_written = true;
				AddInfoToList();
				finish();
				break;

			}
		}
	};

	private void MoveToNextStep() {
		intent = new Intent(QuestMilestone.this, QuestMilestone.class);
		intent.putExtra("count", count + 1);
		startActivity(intent);
		AddInfoToList();
	}

	private void AddInfoToList() {
		QuestCreation.milestones.add(milestone_step.getText().toString().trim());
	}

	private AlertDialog GetFinishStepOption() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				// set message, title, and icon
				.setTitle(StaticClass.TAG_ERROR)
				.setMessage(StaticClass.TAG_CONFIRM_CANCEL)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								try {
									dialog.dismiss();
									finish();
								} catch (Exception e) {
									dialog.dismiss();
								}
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		return myQuittingDialogBox;
	}

	private AlertDialog GetNullDataHandleDialog() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				.setTitle(StaticClass.TAG_ERROR)
				.setMessage("Fill in the blank")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).create();
		return myQuittingDialogBox;
	}

}
