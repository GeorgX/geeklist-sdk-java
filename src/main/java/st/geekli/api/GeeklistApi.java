package st.geekli.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
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
import st.geekli.api.type.GeeklistType;
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
		
		mOAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		mClient = new DefaultHttpClient();
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
		JSONObject response = doRequest("http://sandbox.geekli.st/oauth/authorize", HttpMethod.GET, false);
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
	
	public void getUser(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username), HttpMethod.GET, true);
	}
	
	public void getCards() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("user/cards"), HttpMethod.GET, true);
	}
	
	public void getCards(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username+"/cards"), HttpMethod.GET, true);
	}
	
	public void getCard(String id) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("cards/"+ id), HttpMethod.GET, true);
	}
	
	public void createCard(String content) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("cards"), HttpMethod.POST, true);
	}
	
	public void getMicros() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("user/micros"), HttpMethod.GET, true);
	}
	
	public void getMicros(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username+"/micros"), HttpMethod.GET, true);
	}
	
	public void createMicro(String status) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		doRequest(buildApiRequestUrl("micros"), HttpMethod.POST, true);
	}
	
	public void createMicro(String status, String inReplyTo, MicroType type) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("in_reply_to", inReplyTo);
		params.put("status", status);
		doRequest(buildApiRequestUrl("micros"), HttpMethod.POST, true);
	}
	
	public void getFollowers() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("user/followers"), HttpMethod.GET, true);
	}
	
	public void getFollowers(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username+"/followers"), HttpMethod.GET, true);
	}
	
	public void getFollowing() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("user/following"), HttpMethod.GET, true);
	}
	
	public void getFollowing(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username+"/following"), HttpMethod.GET, true);
	}
	
	public void follow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		params.put("follow", "follow");
		doRequest(buildApiRequestUrl("follow"), HttpMethod.POST, true);
	}
	
	public void unfollow(String username) throws GeeklistApiException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", username);
		doRequest(buildApiRequestUrl("follow"), HttpMethod.POST, true);
	}
	
	public void getActivity() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("user/activity"), HttpMethod.GET, true);
	}
	
	public void getActivity(String username) throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("users/"+username+"/activity"), HttpMethod.GET, true);
	}
	
	public void getAllActivity() throws GeeklistApiException
	{
		doRequest(buildApiRequestUrl("activity"), HttpMethod.GET, true);
	}
	
	public void highfive() throws GeeklistApiException
	{
		
	}
	
	
	private JSONObject doRequest(String url, HttpMethod method, boolean sign) throws GeeklistApiException
	{
		HttpRequestBase request = null;
		
		switch(method)
		{
		case GET:
			request = new HttpGet(url);
			break;
		case POST:
			request = new HttpPost(url);
			break;
		}

		if(sign)
		{
			try {
				mOAuthConsumer.sign(request);
			} catch (OAuthMessageSignerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OAuthExpectationFailedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OAuthCommunicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			HttpResponse response = mClient.execute(request);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				JSONObject responseObject = new JSONObject(Utils.inputStreamToString(response.getEntity().getContent()));
				
				if(responseObject.optString("status").equals("ok"))
				{
					return responseObject.optJSONObject("data");
				} else {
					throw new GeeklistApiException(responseObject.optString("error"));
				}
				
			} else {
				// TODO: return something meaningful
				return null;
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
	
	private String buildApiRequestUrl(String path) throws GeeklistApiException
	{
		return buildApiRequestUrl(path, new HashMap<String,Object>());
	}
	
	private String buildApiRequestUrl(String path, Map<String, Object> params) throws GeeklistApiException
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
