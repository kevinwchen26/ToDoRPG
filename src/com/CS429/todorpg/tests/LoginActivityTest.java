package com.CS429.todorpg.tests;

import com.CS429.todorpg.LoginActivity;
import com.CS429.todorpg.R;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {
	
	private LoginActivity myLoginActivityTest;
	private EditText email;
	private EditText password;
	private static final String login = "login ENTER ";
	private static final String pass = "password ENTER ";

	public LoginActivityTest() {
		super(LoginActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        myLoginActivityTest = getActivity();
        email = (EditText) myLoginActivityTest.findViewById(R.id.email_box);
        password = (EditText) myLoginActivityTest.findViewById(R.id.password_box);
    }

	public void testPreconditions() {
	    assertNotNull("myLoginActivityTest is null", myLoginActivityTest);
	    assertNotNull("email is null", email);
	    assertNotNull("password is null", password);
	}
	
	public void testLogin() {
		boolean response = myLoginActivityTest.checkCredentials("email", "password");
		assertTrue(response);
		sendKeys(login);
		sendKeys(pass);
		sendKeys("ENTER");
		String emailT = email.getText().toString();
		assertFalse("Email should have login as String", emailT.equals("login"));
	}
	//testing

}
