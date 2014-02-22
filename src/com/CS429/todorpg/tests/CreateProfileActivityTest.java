package com.CS429.todorpg.tests;
/*package com.CS429.todorpg.tests;

import static org.junit.Assert.*;

import com.CS429.todorpg.CreateProfileActivity;
import com.CS429.todorpg.R;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

public class CreateProfileActivityTest extends
ActivityInstrumentationTestCase2<CreateProfileActivity> {

	private CreateProfileActivity myCreateProfileActivityTest;
	private EditText email;
	private EditText password;
	private EditText password2;
	private static final String login = "L O G I N ENTER";
	private static final String pass = "P A S S ENTER";
	private static final String pass2 = "P A S S ENTER";

	public CreateProfileActivityTest() {
		super(CreateProfileActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		myCreateProfileActivityTest = getActivity();
		email = (EditText) myCreateProfileActivityTest.findViewById(R.id.new_email_box);
		password = (EditText) myCreateProfileActivityTest.findViewById(R.id.new_password_box);
		password2 = (EditText) myCreateProfileActivityTest.findViewById(R.id.reenter_password_box);
	}

	public void testPreconditions() {
		assertNotNull("myCreateProfileActivityTest is null", myCreateProfileActivityTest);
		assertNotNull("email is null", email);
		assertNotNull("password is null", password);
		assertNotNull("password2 is null", password2);
	}

	public void testAlert() {
		sendKeys(login);
		sendKeys(pass);
		sendKeys(pass2);
		String emailT = email.getText().toString();
		String passT = password.getText().toString();
		String pass2T = password2.getText().toString();
		//System.out.println(emailT);
		assertTrue("Email should have login as String", emailT.equals("login"));
		assertTrue("passT should have pass as String", passT.equals("pass"));
		assertTrue("pass2T should have pass as String", pass2T.equals("pass"));
	}
	//testing

}
*/