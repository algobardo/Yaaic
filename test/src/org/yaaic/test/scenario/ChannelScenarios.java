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

import org.yaaic.activity.ServersActivity;

import android.test.Solo; // CQA, instead of robotium

/**
 * Scenarios regarding channels of a server
 * 
 * @author Sebastian Kaspari <sebastian@yaaic.org>
 */
public class ChannelScenarios extends ActivityInstrumentationTestCase2<ServersActivity> {
	private Solo solo;
	private ScenarioHelper helper;
	
	/**
	 * Create a new ChannelScenario instance
	 */
	public ChannelScenarios() {
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
	 * Scenario: Write a channel message
	 * 
	 * - Connect to server
	 * - Join test channel
	 * - Write a message
	 * - The message is displayed in the view
	 */
	public void testSendingChannelMessage() {
		helper.joinTestChannel();
		helper.send("Hello Test-World");
		assertTrue(solo.searchText("<YaaicBotium> Hello Test-World"));
	}
}
