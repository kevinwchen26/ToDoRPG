package com.CS429.todorpg.AndroidJUnitTests;

import junit.framework.Test;
import android.test.suitebuilder.TestSuiteBuilder;

public class TestSuite {

	public static Test suite() {;
		return new TestSuiteBuilder(TestSuite.class).includeAllPackagesUnderHere().build();
	}
}
