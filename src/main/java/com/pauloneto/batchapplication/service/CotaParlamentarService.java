package com.pauloneto.batchapplication.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pauloneto.batchapplication.models.CotaParlamentar;
import com.pauloneto.batchapplication.repository.nosql.CotaParlamentarRepository;
import com.pauloneto.batchapplication.util.FileUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CotaParlamentarService {

    @Inject
    private Logger logger;

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private CotaParlamentarRepository repository;

    public void importar() {
        try {
            Gson gson = new Gson();
            InputStream isJson = FileUtil.readFileAsInputStream(CotaParlamentarService.class,"Ano-2019.json");
            JsonReader reader = new JsonReader(new InputStreamReader(isJson, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                CotaParlamentar cota = gson.fromJson(reader, CotaParlamentar.class);
                repository.save(cota);
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, ExceptionUtils.getRootCauseMessage(e),ExceptionUtils.getRootCause(e));
            throw new IllegalStateException(ExceptionUtils.getRootCauseMessage(e),ExceptionUtils.getRootCause(e));
        }
    }
}
