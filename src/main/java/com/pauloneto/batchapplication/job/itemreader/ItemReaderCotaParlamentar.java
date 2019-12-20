package com.pauloneto.batchapplication.job.itemreader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.pauloneto.batchapplication.deserialize.DateDeserializer;
import com.pauloneto.batchapplication.models.CotaParlamentar;
import com.pauloneto.batchapplication.service.CotaParlamentarService;
import com.pauloneto.batchapplication.util.FileUtil;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.AbstractItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;

@Dependent
@Named("itemReaderCotaParlamentar")
public class ItemReaderCotaParlamentar extends AbstractItemReader {

    private Gson gson;
    private InputStream isJson;
    private JsonReader jsonReader;

    @PostConstruct
    public void init(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class,new DateDeserializer());
        gson = gsonBuilder.create();
    }

    @Override
    public void open(Serializable prevCheckpointInfo) throws Exception{
        isJson = FileUtil.readFileAsInputStream(CotaParlamentarService.class,"Ano-2019.json");
        jsonReader = new JsonReader(new InputStreamReader(isJson, "UTF-8"));
        jsonReader.beginArray();
    }

    @Override
    public Object readItem() throws Exception {
        if(jsonReader.hasNext()){
            return gson.fromJson(jsonReader, CotaParlamentar.class);
        }
        return null;
    }

    @Override
    public void close()throws Exception{
        jsonReader.endArray();
        jsonReader.close();
    }
}
