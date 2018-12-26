package com.zws.core.token.store;


import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.List;

public interface IndexNameOauth2Store extends TokenStore {

    String PRINCIPAL_NAME_INDEX_NAME = IndexNameOauth2Store.class.getName()
            .concat(".PRINCIPAL_NAME_INDEX_NAME");

    void saveIndexName(String indexName,String indexValue);


    List<OAuth2AccessToken> getAllAccessToken(String indexName);




}
