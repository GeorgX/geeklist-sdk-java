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

package st.geekli.api;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import st.geekli.api.type.Activity;
import st.geekli.api.type.Card;
import st.geekli.api.type.Micro;
import st.geekli.api.type.MicroType;
import st.geekli.api.type.User;

public class GeeklistApi {

	public static final String VERSION_1 = "v1";	
	private static final String API_URL = "http://sandbox-api.geekli.st/";
	private static final String DEFAULT_USER_AGENT = "Geekli.st for Java/1.0";
	
	private boolean mUseCallback = true;
	private String mVersion = VERSION_1;
	private String mUserAgent = DEFAULT_USER_AGENT;

	private DefaultHttpClient mClient;
	private OAuthProvider mOAuthProvider = new CommonsHttpOAuthProvider(
			"http://sandbox-api.geekli.st/v1/oauth/request_token",
			"http://sandbox-api.geekli.st/v1/oauth/access_token",
			"http://sandbox.geekli.st/oauth/authorize"
			);
	private OAuthConsumer mOAuthConsumer;
	
	/**
	 * Constructor.
	 * 
	 * @param consumerKey OAuth Consumer Key
	 * @param consumerSecret OAuth Consumer Secret
	 */
	public GeeklistApi(String consumerKey, String consumerSecret, boolean useCallback) {
		mUseCallback = useCallback;
		mClient = new DefaultHttpClient();
		mOAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
	}
	
	public GeeklistApi(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, boolean useCallback) {
		this(consumerKey, consumerSecret, useCallback);
		mOAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
	}
	
	public String getOAuthToken() {
		return mOAuthConsumer.getToken();
	}
	
	public String getOAuthTokenSecret() {
		return mOAuthConsumer.getTokenSecret();
	}

	public void setOAuthTokenAndSecret(String token, String tokenSecret) {
		mOAuthConsumer.setTokenWithSecret(token, tokenSecret);
	}
	
	public String getRequestToken(String callbackUrl) throws GeeklistApiException
	{
		try {
			if(mUseCallback) {
				return mOAuthProvider.retrieveRequestToken(mOAuthConsumer, callbackUrl);
			} else {
				return mOAuthProvider.retrieveRequestToken(mOAuthConsumer, OAuth.OUT_OF_BAND);
			}
		} catch (OAuthMessageSignerException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthNotAuthorizedException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthCommunicationException e) {
			throw new GeeklistApiException(e);
		}
	}
	
	public String authorize(String requestToken) throws GeeklistApiException
	{
		doRequest("http://sandbox.geekli.st/oauth/authorize", HttpMethod.GET, false);
		return "";
	}
	
	public void getAccessToken(String requestToken, String oauthVerifier) throws GeeklistApiException
	{
		try {
			mOAuthProvider.retrieveAccessToken(mOAuthConsumer, oauthVerifier);
		} catch (OAuthMessageSignerException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthNotAuthorizedException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new GeeklistApiException(e);
		} catch (OAuthCommunicationException e) {
			throw new GeeklistApiException(e);
		}
	}
	
	public User getUser() throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("user"), HttpMethod.GET, true);
		return (User) ResponseParser.parseObject(User.class, response, true);
	}
	
	public User getUser(String username) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username), HttpMethod.GET, true);
		return (User) ResponseParser.parseObject(User.class, response, true);
	}
	
	public Card[] getCards(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("user/cards"), params, HttpMethod.GET, true);
		return (Card[]) ResponseParser.parseObjects(Card.class, response.optJSONArray("cards"), true);
	}
	
	public Card[] getCards() throws GeeklistApiException
	{
		return getCards(1, 10);
	}
	
	public Card[] getCards(String username, int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/cards"), params, HttpMethod.GET, true);
		return (Card[]) ResponseParser.parseObjects(Card.class, response.optJSONArray("cards"), true);
	}
	
	public Card[] getCards(String username) throws GeeklistApiException
	{
		return getCards(username, 1, 10);
	}
	
	public Card getCard(String id) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("cards/"+ id), HttpMethod.GET, true);
		return (Card) ResponseParser.parseObject(Card.class, response, true);
	}
	
	public Card createCard(String headline) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("headline", headline);
		JSONObject response = doRequest(buildApiRequestUrl("cards"), params, HttpMethod.POST, true);
		return (Card) ResponseParser.parseObject(Card.class, response, true);
	}
	
	public Micro[] getMicros(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("user/micros"), params, HttpMethod.GET, true);
		return (Micro[]) ResponseParser.parseObjects(Micro.class, response.optJSONArray("micros"), true);
	}
	
	public Micro[] getMicros() throws GeeklistApiException
	{
		return getMicros(1, 10);
	}
	
	public Micro[] getMicros(String username, int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/micros"), params, HttpMethod.GET, true);
		return (Micro[]) ResponseParser.parseObjects(Micro.class, response.optJSONArray("micros"), true);
	}
	
	public Micro[] getMicros(String username) throws GeeklistApiException
	{
		return getMicros(username, 1, 10);
	}
	
	public Micro createMicro(String status) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		JSONObject response = doRequest(buildApiRequestUrl("micros"), params, HttpMethod.POST, true);
		return (Micro) ResponseParser.parseObject(Micro.class, response, true);
	}
	
	public Micro createMicro(String status, String inReplyTo, MicroType type) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("in_reply_to", inReplyTo);
		params.put("status", status);
		JSONObject response = doRequest(buildApiRequestUrl("micros"), params, HttpMethod.POST, true);
		return (Micro) ResponseParser.parseObject(Micro.class, response, true);
	}
	
	public User[] getFollowers(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("user/followers"), params, HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("followers"), true);
	}
	
	public User[] getFollowers() throws GeeklistApiException
	{
		return getFollowers(1, 10);
	}
	
	public User[] getFollowers(String username, int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/followers"), params, HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("followers"), true);
	}
	
	public User[] getFollowers(String username) throws GeeklistApiException
	{
		return getFollowers(username, 1, 10);
	}
	
	public User[] getFollowing(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("user/following"), params, HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("following"), true);
	}
	
	public User[] getFollowing() throws GeeklistApiException
	{
		return getFollowing(1, 10);
	}
	
	public User[] getFollowing(String username, int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/following"), params, HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("following"), true);
	}
	
	public User[] getFollowing(String username) throws GeeklistApiException
	{
		return getFollowing(username, 1, 10);
	}
	
	public void follow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		params.put("action", "follow");
		doRequest(buildApiRequestUrl("follow"), params, HttpMethod.POST, true);
	}
	
	public void unfollow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		doRequest(buildApiRequestUrl("follow"), params, HttpMethod.POST, true);
	}
	
	public Activity[] getActivity(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONArray response = doRequestArray(buildApiRequestUrl("user/activity"), params, HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public Activity[] getActivity() throws GeeklistApiException
	{
		return getActivity(1, 10);
	}
	
	public Activity[] getActivity(String username, int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONArray response = doRequestArray(buildApiRequestUrl("users/"+username+"/activity"), params, HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public Activity[] getActivity(String username) throws GeeklistApiException
	{
		return getActivity(username, 1, 10);
	}
	
	public Activity[] getAllActivity(int page, int count) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("count", count);
		JSONArray response = doRequestArray(buildApiRequestUrl("activity"), params, HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public Activity[] getAllActivity() throws GeeklistApiException
	{
		return getAllActivity(1, 10);
	}
	
	public void highfive(String type, String id) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("gfk", id);
		doRequest(buildApiRequestUrl("highfive"), params, HttpMethod.POST, true);
	}
	
	private JSONObject doRequest(String url, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		return doRequest(url, new HashMap<String,Object>(), method, sign);
	}
	
	private JSONObject doRequest(String url, HashMap<String,Object> params, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		 return (JSONObject)internalDoRequest(url, params, method, sign);
	}
	
	private Object internalDoRequest(String url, HashMap<String,Object> params, HttpMethod method, boolean sign) throws GeeklistApiException
	{	
		HttpRequestBase request = null;
		
		switch(method)
		{
		case GET:
			StringBuilder sb = new StringBuilder();
			sb.append(url);
			if(params.size() > 0)
			{
				sb.append("?");
				
				for (Iterator<Map.Entry<String, Object>> i = params.entrySet().iterator(); i.hasNext(); )
				{
					Map.Entry<String, Object> param = i.next();
					try {
						sb.append(param.getKey());
						sb.append('=');
						sb.append(URLEncoder.encode(param.getValue().toString(),"UTF-8"));
						
						if(i.hasNext())
							sb.append('&');
					} catch (UnsupportedEncodingException e) {
						throw new GeeklistApiException(e);
					}
				}
			}
			
			request = new HttpGet(sb.toString());
			break;
		case POST:
			request = new HttpPost(url);
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			ArrayList<BasicNameValuePair> postParams = new ArrayList<BasicNameValuePair>();
			
			if(params.size() > 0)
			{
				for(Map.Entry<String, Object> param : params.entrySet())
				{
					postParams.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
				}
			}
			UrlEncodedFormEntity urlEncodedFormEntity;
			try {
				urlEncodedFormEntity = new UrlEncodedFormEntity(postParams);
				urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
			    ((HttpPost)request).setEntity(urlEncodedFormEntity);
			} catch (UnsupportedEncodingException e2) {
				throw new GeeklistApiException(e2);
			}
			break;
		}

		request.setHeader("User-Agent", mUserAgent);
		request.setHeader("Accept-Charset", "UTF-8");

		if(sign)
		{
			try {
				mOAuthConsumer.sign(request);
			} catch (OAuthMessageSignerException e1) {
				throw new GeeklistApiException(e1);
			} catch (OAuthExpectationFailedException e1) {
				throw new GeeklistApiException(e1);
			} catch (OAuthCommunicationException e1) {
				throw new GeeklistApiException(e1);
			}
		}
			
		try {
			HttpResponse response = mClient.execute(request);
				
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				JSONObject responseObject = new JSONObject(Utils.inputStreamToString(response.getEntity().getContent()));
					
				if(responseObject.optString("status").equals("ok"))
				{
					JSONObject obj = responseObject.optJSONObject("data");
					
					if(obj != null)
					{
						return obj;
					} else {
						return responseObject.optJSONArray("data");
					}
					
				} else {
					throw new GeeklistApiException(responseObject.optString("error"));
				}
					
			} else {
				throw new GeeklistApiException(response.getStatusLine().getReasonPhrase());
			}
				
		} catch (ClientProtocolException e) {
				throw new GeeklistApiException(e);
		} catch (IOException e) {
				throw new GeeklistApiException(e);
		} catch (IllegalStateException e) {
				throw new GeeklistApiException(e);
		} catch (JSONException e) {
				throw new GeeklistApiException(e);
		}
	}
	
	private JSONArray doRequestArray(String url, HashMap<String,Object> params, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		 return (JSONArray)internalDoRequest(url, params, method, sign);
	}
	
	private JSONArray doRequestArray(String url, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		 return (JSONArray)internalDoRequest(url, new HashMap<String,Object>(), method, sign);
	}
	
	private String buildApiRequestUrl(String path) throws GeeklistApiException
	{
		StringBuilder sb = new StringBuilder(API_URL);
		sb.append(mVersion);
		sb.append("/");
		sb.append(path);

		return sb.toString();
	}
	
	public void setVersion(String version)
	{
		mVersion = version;
	}
	
	public String getVersion()
	{
		return mVersion;
	}
}
