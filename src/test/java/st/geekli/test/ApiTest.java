/*
 * Copyright (C) 2012 Stefan Hoth, Sebastian Mauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package st.geekli.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import st.geekli.api.GeeklistApi;
import st.geekli.api.GeeklistApiException;
import st.geekli.api.type.Activity;
import st.geekli.api.type.Card;
import st.geekli.api.type.Micro;
import st.geekli.api.type.User;

public class ApiTest {

	// Enter your API credentials here.
	private static final String CONSUMER_KEY = "<consumerkey>";
	private static final String CONSUMER_SECRET = "<consumer_secret>";
	private static final String TOKEN = "<token>";
	private static final String TOKEN_SECRET = "<token_secret>";
	
	// Objects used for testing
	private static final String EXPECTED_USER = "mauimauer";
	private static final String OTHER_USER = "stefanhoth";
	private static final String FOLLOW_USER = "8eb84d80be1f6fc88c20bf17b6f1e30e088be4d9684be95641c0184e8b2eb063"; //TechWraith
	private static final String CARD_ID = "434c8603250f97cb28d769a3d882b5633f4624b5f89cd1277ab62b358ac71ab3";
	private static final String HIGHFIVE_ITEM = "d0df3e63c9cb66f3fb4e0c15d93a003796c97c0c6600c1e86392730d6e50ee89";
		
	private static GeeklistApi client;
	
	@BeforeClass public static void beforeClass()
	{
		client = new GeeklistApi(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET, true);
	}
	
	@Test public void testGetUser()
	{
		try {
			User myself = client.getUser();
			assertNotNull("User should not be null...are you using valid OAuth credentials?", myself);
			assertEquals("API returned wrong username (check EXPECTED USER)",myself.getScreenName(), EXPECTED_USER);
		} catch (GeeklistApiException e) {
			fail("getUser(myself) failed!");
		}
	}
	
	@Test public void testGetOtherUser()
	{
		try {
			User otherUser = client.getUser(OTHER_USER);
			assertNotNull("User should not be null...does OTHER_USER exist?",otherUser);
			assertEquals("API returned wrong username (check OTHER_USER)", otherUser.getScreenName(), OTHER_USER);
		} catch (GeeklistApiException e) {
			fail("getUser(username) failed!");
		}
	}
	
	@Test public void testGetCards()
	{
		try {
			Card[] cards = client.getCards();
			assertNotNull("getCards() should not return null. We want an empty array?", cards);
			
			if(cards.length > 0)
			{
				assertNotNull("id can't be null!", cards[0].getId());
			}
			
		} catch (GeeklistApiException e) {
			fail("getCards() failed!");
		}
	}
	
	@Test public void testGetOtherCards()
	{
		try {
			Card[] cards = client.getCards(OTHER_USER);
			assertNotNull("getCards(username) should not return null. We want an empty array?", cards);
			
			if(cards.length > 0)
			{
				assertNotNull("id can't be null!", cards[0].getId());
			}
			
		} catch (GeeklistApiException e) {
			fail("getCards(username) failed!");
		}
	}
	
	@Test public void testGetSpecificCard()
	{
		try {
			Card card = client.getCard(CARD_ID);
			assertNotNull("getCard(id) should not return null. Does your CARD_ID exist?", card);
			assertNotNull("id can't be null!", card.getId());
			
		} catch (GeeklistApiException e) {
			fail("getCard(id) failed!");
		}
	}
	
	@Test public void testCreateCard()
	{
		try {
			Card newCard = client.createCard("JUnit Testcard - "+(int)(Math.random()*100));
			assertNotNull("Creating a card failed!", newCard);
			assertNotNull("id of new card can't be null!", newCard.getId());
		} catch (GeeklistApiException e) {
			fail("createCard(headline) failed!");
		}
	}
	
	@Test public void testGetMicros()
	{
		try {
			Micro[] micros = client.getMicros();
			assertNotNull("getMicros() should not return null. We want an empty array?", micros);
			
			if(micros.length > 0)
			{
				assertNotNull("id can't be null!", micros[0].getId());
			}
			
		} catch (GeeklistApiException e) {
			fail("getMicros() failed!");
		}
	}
	
	@Test public void testGetOtherMicros()
	{
		try {
			Micro[] micros = client.getMicros(OTHER_USER);
			assertNotNull("getMicros(username) should not return null. We want an empty array?", micros);
			
			if(micros.length > 0)
			{
				assertNotNull("id can't be null!", micros[0].getId());
			}
			
		} catch (GeeklistApiException e) {
			fail("getMicros(username) failed!");
		}
	}
	
	@Test public void testCreateMicro()
	{
		try {
			Micro micro = client.createMicro("JUnit Micro - "+(int)(Math.random()*100));
			assertNotNull("Creating a micro failed!", micro);
			assertNotNull("id of new micro can't be null!", micro.getId());
		} catch (GeeklistApiException e) {
			fail("createMicro(status) failed!");
		}
	}
	
	@Test public void testGetFollowers()
	{
		try {
			User[] followers = client.getFollowers();
			
			assertNotNull("getFollowers() should not return null. We want an empty array?", followers);
			
			if(followers.length > 0)
			{
				assertNotNull("id can't be null!", followers[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getFollowers() failed!");
		}
	}
	
	@Test public void testGetOtherFollowers()
	{
		try {
			User[] followers = client.getFollowers(OTHER_USER);
			
			assertNotNull("getFollowers(username) should not return null. We want an empty array?", followers);
			
			if(followers.length > 0)
			{
				assertNotNull("id can't be null!", followers[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getFollowers(username) failed!");
		}
	}
	
	@Test public void testGetFollowing()
	{
		try {
			User[] following = client.getFollowing();
			
			assertNotNull("getFollowing() should not return null. We want an empty array?", following);
			
			if(following.length > 0)
			{
				assertNotNull("id can't be null!", following[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getFollowing() failed!");
		}
	}
	
	@Test public void testGetOtherFollowing()
	{
		try {
			User[] following = client.getFollowing(OTHER_USER);
			
			assertNotNull("getFollowing(username) should not return null. We want an empty array?", following);
			
			if(following.length > 0)
			{
				assertNotNull("id can't be null!", following[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getFollowing(username) failed!");
		}
	}
	
	@Test public void testFollow()
	{
		try {
			client.follow(FOLLOW_USER);
		} catch (GeeklistApiException e) {
			fail("follow(username) failed!");
		}
	}
	
	@Test public void testUnFollow()
	{
		
		try {
			client.unfollow(FOLLOW_USER);
		} catch (GeeklistApiException e) {
			fail("unfollow(username) failed!");
		}
	}
	
	@Test public void testGetActivity()
	{
		try {
			Activity[] activities = client.getActivity();
			
			assertNotNull("getActivity() should not return null. We want an empty array?", activities);
			
			if(activities.length > 0)
			{
				assertNotNull("id can't be null!", activities[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getActivity() failed!");
		}
	}
	
	@Test public void testGetOtherActivity()
	{
		try {
			Activity[] activities = client.getActivity(OTHER_USER);
			
			assertNotNull("getActivity(username) should not return null. We want an empty array?", activities);
			
			if(activities.length > 0)
			{
				assertNotNull("id can't be null!", activities[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getActivity(username) failed!");
		}
	}
	
	@Test public void testGetAllActivity()
	{
		try {
			Activity[] activities = client.getAllActivity();
			
			assertNotNull("getActivity(username) should not return null. We want an empty array?", activities);
			
			if(activities.length > 0)
			{
				assertNotNull("id can't be null!", activities[0].getId());
			}
		} catch (GeeklistApiException e) {
			fail("getActivity(username) failed!");
		}
	}
	
	@Test public void testHighfive()
	{
		try {
			client.highfive("card", HIGHFIVE_ITEM);
		} catch (GeeklistApiException e) {
			fail("highfive(type, id) failed!");
		}
	}
	
	@AfterClass public static void afterClass()
	{
		client = null;
	}
}
