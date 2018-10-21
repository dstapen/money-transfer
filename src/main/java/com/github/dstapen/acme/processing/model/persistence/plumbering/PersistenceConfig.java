package com.github.dstapen.acme.processing.model.persistence.plumbering;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("persistence.jdbc")
public class PersistenceConfig {
    private String url;
    private String db;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
