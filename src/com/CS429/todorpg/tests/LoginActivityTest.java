package com.CS429.todorpg.tests;
/*package com.CS429.todorpg.tests;

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
	private static final String login = "L O G I N ENTER";
	private static final String pass = "P A S S ENTER";

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
	
	public void testDefault() {
		boolean response = myLoginActivityTest.checkCredentials("login", "pass");
		assertFalse(response);
	}
	
	public void testUser() {
		boolean response = myLoginActivityTest.checkCredentials("login", "");
		assertFalse(response);
	}
	
	public void testPass() {
		boolean response = myLoginActivityTest.checkCredentials("", "pass");
		assertFalse(response);
	}
	
	public void testLogin() {
		sendKeys(login);
		sendKeys(pass);
		String emailT = email.getText().toString();
		String passT = password.getText().toString();
		assertTrue("Email should have login as String", emailT.equals("login"));
		assertTrue("passT should have pass as String", passT.equals("pass"));
	}
	//testing

}
*/