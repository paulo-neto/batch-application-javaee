package com.pauloneto.batchapplication.deserialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateDeserializer implements JsonDeserializer<Date> {

    private Logger logger = Logger.getLogger(DateDeserializer.class.getName());

    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            String date = element.getAsString();
            if(date == null || date.isEmpty())
                return null;

            date = date.replace("T"," ");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return format.parse(date);
        } catch (ParseException exp) {
            logger.log(Level.SEVERE,"Failed to parse Date:", exp);
            return null;
        }
    }
}
