package com.pauloneto.batchapplication.producer;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.Serializable;

@Singleton
@Startup
public class StartupBean implements Serializable {

    @PostConstruct
    public void create() {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "com.pauloneto.apirest.models");
        System.setProperty("org.apache.activemq.artemis.jms.deserialization.whitelist", "com.pauloneto.apirest.models");
    }
}
