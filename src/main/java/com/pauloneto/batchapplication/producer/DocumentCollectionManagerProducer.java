package com.pauloneto.batchapplication.producer;

import org.jnosql.diana.api.document.DocumentCollectionManager;
import org.jnosql.diana.api.document.DocumentCollectionManagerFactory;
import org.jnosql.diana.api.document.DocumentConfiguration;
import org.jnosql.diana.mongodb.document.MongoDBDocumentConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class DocumentCollectionManagerProducer {

    private static final String DATA_BASE = "local";

    @Inject
    private Logger logger;

    private DocumentConfiguration configuration;

    private DocumentCollectionManagerFactory managerFactory;

    @PostConstruct
    public void init() {
        configuration = new MongoDBDocumentConfiguration();
        managerFactory = configuration.get(); // Properties coming from diana-mongodb.properties
        logger.log(Level.INFO, "calling init, managerFactory={0}", managerFactory);
    }

    // This enables to inject DocumentTemplate and repository objects
    @Produces
    public DocumentCollectionManager getManager() {
        return managerFactory.get(DATA_BASE);
    }


    @PreDestroy
    public void closeFactory() {
        if ( managerFactory!=null )
            managerFactory.close();
    }
}
