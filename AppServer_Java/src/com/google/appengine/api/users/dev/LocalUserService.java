package com.google.appengine.api.users.dev;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.google.appengine.tools.development.AbstractLocalRpcService;
import com.google.appengine.tools.development.LocalRpcService;
import com.google.appengine.tools.development.LocalServiceContext;
import com.google.appengine.tools.development.ServiceProvider;
import com.google.apphosting.api.UserServicePb;


@ServiceProvider(LocalRpcService.class)
public final class LocalUserService extends AbstractLocalRpcService
{
	/*
	 * AppScale - every method in this class has been modified
	 */
	private static final String	LOGIN_URL		= "/_ah/login";
	private static final String	CONSUMER_KEY	= "example.com";
	private static final String	EMAIL			= "example@example.com";
	private static final String	USER_ID			= "0";
	private static final String	AUTH_DOMAIN		= "gmail.com";
	public static final String	PACKAGE			= "user";
	private static final String	login_server	= System.getProperty("LOGIN_SERVER");

	public UserServicePb.CreateLoginURLResponse createLoginURL( LocalRpcService.Status status, UserServicePb.CreateLoginURLRequest request )
	{
		UserServicePb.CreateLoginURLResponse response = new UserServicePb.CreateLoginURLResponse();
		response.setLoginUrl(LOGIN_URL + "?continue=" + encode(request.getDestinationUrl()));

		return response;
	}

	public UserServicePb.CreateLogoutURLResponse createLogoutURL( LocalRpcService.Status status, UserServicePb.CreateLogoutURLRequest request )
	{
		UserServicePb.CreateLogoutURLResponse response = new UserServicePb.CreateLogoutURLResponse();
		String redirect_url = "http://" + login_server + "/logout";
		response.setLogoutUrl(redirect_url);

		return response;
	}

	public UserServicePb.CheckOAuthSignatureResponse checkOAuthSignature( LocalRpcService.Status status, UserServicePb.CheckOAuthSignatureRequest request )
	{
		UserServicePb.CheckOAuthSignatureResponse response = new UserServicePb.CheckOAuthSignatureResponse();
		response.setOauthConsumerKey(CONSUMER_KEY);
		return response;
	}

	public UserServicePb.GetOAuthUserResponse getOAuthUser( LocalRpcService.Status status, UserServicePb.GetOAuthUserRequest request )
	{
		UserServicePb.GetOAuthUserResponse response = new UserServicePb.GetOAuthUserResponse();
		response.setEmail(EMAIL);
		response.setUserId(USER_ID);
		response.setAuthDomain(AUTH_DOMAIN);
		return response;
	}

	public String getPackage()
	{
		return "user";
	}

	public void init( LocalServiceContext context, Map<String, String> properties )
	{}

	public void start()
	{}

	public void stop()
	{}

	private static String encode( String url )
	{
		try
		{
			return URLEncoder.encode(url, "UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			throw new RuntimeException("Could not find UTF-8 encoding", ex);
		}
	}
}