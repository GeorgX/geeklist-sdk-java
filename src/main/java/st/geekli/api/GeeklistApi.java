package st.geekli.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
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

	private OAuthProvider mOAuthProvider = new DefaultOAuthProvider(
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
		
		mOAuthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
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
			if(mUseCallback)
			{
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
		try {
			doRequest(new URL("http://sandbox.geekli.st/oauth/authorize"), HttpMethod.GET, false);
		} catch (MalformedURLException e) {
			throw new GeeklistApiException(e);
		}
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
	
	public Card[] getCards() throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("user/cards"), HttpMethod.GET, true);
		return (Card[]) ResponseParser.parseObjects(Card.class, response.optJSONArray("cards"), true);
	}
	
	public Card[] getCards(String username) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/cards"), HttpMethod.GET, true);
		return (Card[]) ResponseParser.parseObjects(Card.class, response.optJSONArray("cards"), true);
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
		JSONObject response = doRequest(buildApiRequestUrl("cards", params), HttpMethod.POST, true);
		return (Card) ResponseParser.parseObject(Card.class, response, true);
	}
	
	public Micro[] getMicros() throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("user/micros"), HttpMethod.GET, true);
		return (Micro[]) ResponseParser.parseObjects(Micro.class, response.optJSONArray("micros"), true);
	}
	
	public Micro[] getMicros(String username) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/micros"), HttpMethod.GET, true);
		return (Micro[]) ResponseParser.parseObjects(Micro.class, response.optJSONArray("micros"), true);
	}
	
	public Micro createMicro(String status) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		JSONObject response = doRequest(buildApiRequestUrl("micros", params), HttpMethod.POST, true);
		return (Micro) ResponseParser.parseObject(Micro.class, response, true);
	}
	
	public Micro createMicro(String status, String inReplyTo, MicroType type) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("in_reply_to", inReplyTo);
		params.put("status", status);
		JSONObject response = doRequest(buildApiRequestUrl("micros", params), HttpMethod.POST, true);
		return (Micro) ResponseParser.parseObject(Micro.class, response, true);
	}
	
	public User[] getFollowers() throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("user/followers"), HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("followers"), true);
	}
	
	public User[] getFollowers(String username) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/followers"), HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("followers"), true);
	}
	
	public User[] getFollowing() throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("user/following"), HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("following"), true);
	}
	
	public User[] getFollowing(String username) throws GeeklistApiException
	{
		JSONObject response = doRequest(buildApiRequestUrl("users/"+username+"/following"), HttpMethod.GET, true);
		return (User[]) ResponseParser.parseObjects(User.class, response.optJSONArray("following"), true);
	}
	
	public void follow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		params.put("follow", "follow");
		doRequest(buildApiRequestUrl("follow", params), HttpMethod.POST, true);
	}
	
	public void unfollow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		doRequest(buildApiRequestUrl("follow", params), HttpMethod.POST, true);
	}
	
	public Activity[] getActivity() throws GeeklistApiException
	{
		JSONArray response = doRequestArray(buildApiRequestUrl("user/activity"), HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public Activity[] getActivity(String username) throws GeeklistApiException
	{
		JSONArray response = doRequestArray(buildApiRequestUrl("users/"+username+"/activity"), HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public Activity[] getAllActivity() throws GeeklistApiException
	{
		JSONArray response = doRequestArray(buildApiRequestUrl("activity"), HttpMethod.GET, true);
		return (Activity[]) ResponseParser.parseObjects(Activity.class, response, true);
	}
	
	public void highfive(String type, String id) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("gfk", id);
		doRequest(buildApiRequestUrl("highfive"), HttpMethod.POST, true);
	}
	
	private JSONObject doRequest(URL url, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		HttpURLConnection request = null;
		try {
			request = (HttpURLConnection)url.openConnection();

			try {
				switch(method)
				{
				case GET:
					request.setRequestMethod("POST");
					break;
				case POST:
					request.setRequestMethod("POST");
					break;
				}
			} catch (ProtocolException e2) {
				throw new GeeklistApiException(e2);
			}
	
			request.setRequestProperty("User-Agent", mUserAgent);
			request.setRequestProperty("Accept-Charset", "UTF-8");
			
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
				request.connect();
	
				if(request.getResponseCode() == 200)
				{
					JSONObject responseObject = new JSONObject(Utils.inputStreamToString(request.getInputStream()));
					
					if(responseObject.optString("status").equals("ok"))
					{
						return responseObject.optJSONObject("data");
					} else {
						throw new GeeklistApiException(responseObject.optString("error"));
					}
					
				} else {
					throw new GeeklistApiException(request.getResponseMessage(), request.getResponseCode());
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
		
		} catch (IOException e3) {
			throw new GeeklistApiException(e3);
		} finally {

		      if(request != null) {
		    	  request.disconnect(); 
		      }
		}
	}
	
	private JSONArray doRequestArray(URL url, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		HttpURLConnection request = null;
		try {
			request = (HttpURLConnection)url.openConnection();

			try {
				switch(method)
				{
				case GET:
					request.setRequestMethod("POST");
					break;
				case POST:
					request.setRequestMethod("POST");
					break;
				}
			} catch (ProtocolException e2) {
				throw new GeeklistApiException(e2);
			}
	
			request.setRequestProperty("User-Agent", mUserAgent);
			request.setRequestProperty("Accept-Charset", "UTF-8");
			
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
				request.connect();
	
				if(request.getResponseCode() == 200)
				{
					JSONObject responseObject = new JSONObject(Utils.inputStreamToString(request.getInputStream()));
					
					if(responseObject.optString("status").equals("ok"))
					{
						return responseObject.optJSONArray("data");
					} else {
						throw new GeeklistApiException(responseObject.optString("error"));
					}
					
				} else {
					throw new GeeklistApiException(request.getResponseMessage(), request.getResponseCode());
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
		
		} catch (IOException e3) {
			throw new GeeklistApiException(e3);
		} finally {

		      if(request != null) {
		    	  request.disconnect(); 
		      }
		}
	}
	
	private URL buildApiRequestUrl(String path) throws GeeklistApiException
	{
		return buildApiRequestUrl(path, new HashMap<String,Object>());
	}
	
	private URL buildApiRequestUrl(String path, Map<String, Object> params) throws GeeklistApiException
	{
		StringBuilder sb = new StringBuilder(API_URL);
		sb.append(mVersion);
		sb.append("/");
		sb.append(path);
		
		if(params.size() > 0)
		{
			sb.append('?');
			for(Map.Entry<String, Object> param : params.entrySet())
			{
				try {
					sb.append(param.getKey());
					sb.append('=');
					sb.append(URLEncoder.encode(param.getValue().toString(),"UTF-8"));
					sb.append('&');
				} catch (UnsupportedEncodingException e) {
					throw new GeeklistApiException(e);
				}
			}
		}
	
		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			throw new GeeklistApiException(e);
		}
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
