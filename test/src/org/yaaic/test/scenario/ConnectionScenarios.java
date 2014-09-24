/*
Yaaic - Yet Another Android IRC Client

Copyright 2009-2010 Sebastian Kaspari

This file is part of Yaaic.

Yaaic is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Yaaic is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Yaaic.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.yaaic.test.scenario;



import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.test.Solo; // CQA, instead of robotium

import org.yaaic.activity.ServersActivity;

/**
 * Test scenarios including connecting to a server
 * 
 * @author Sebastian Kaspari <sebastian@yaaic.org>
 */
public class ConnectionScenarios extends ActivityInstrumentationTestCase2<ServersActivity>
{
	private ScenarioHelper helper;
	private Solo solo;
	
	/**
	 * Create a new ConnectionScenario instance
	 */
	public ConnectionScenarios() {
		super(ServersActivity.class);
	}
	
	/**
	 * Setup test case
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // CQA
		if (solo == null) {
			solo   = new Solo(getInstrumentation(), getActivity());
			helper = new ScenarioHelper(solo);
		}
		helper.createTestServer();
		helper.connectToServer();
	}
	
	/**
	 * Cleanup after test
	 */
	@Override
	protected void tearDown() throws Exception {
		helper.disconnectFromServer();
		helper.deleteTestServer();
		solo.finishOpenedActivities();
		super.tearDown(); // CQA
	}
	
	/**
	 * Scenario: Join a channel
	 * 
	 * - Connect to server
	 * - Enter command: /j #yaaic-test
	 * - A new conversation with text #yaaic-test appears
	 * - Disconnect
	 */
	public void testJoiningChannel() {
		// Join channel
		solo.enterText(0, "/j #yaaic-test");
		solo.sendKey(Solo.ENTER);
		
		solo.sleep(1500);

		// Assert channel joined
		assertTrue(solo.searchText("#yaaic-test"));
	}
}
