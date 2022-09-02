package com.odeyalo.analog.neflix.subscriptionservice.config.security;

/**
 * Resolve information about user from token and returns it
 */
public interface JwtTokenAuthenticatedUserInformationResolver {

    AuthenticatedUserInformation getInfo(String token);

}
