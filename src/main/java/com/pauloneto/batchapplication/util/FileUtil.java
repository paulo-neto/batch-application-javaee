package com.pauloneto.batchapplication.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class FileUtil {

    private static final String META_INF_FOLDER = "/META-INF/";
    private static Logger log = Logger.getLogger(FileUtil.class.getName());

    private FileUtil() {
    }

    public static <T> String readFileAsString(final Class<T> clazz, final String fileName) {
        String fileAsString = "";
        final InputStream resourceAsStream = clazz.getClassLoader().getResourceAsStream(META_INF_FOLDER + fileName);
        try {
            fileAsString = new String(IOUtils.toString(resourceAsStream, Charset.forName("UTF-8")));
        } catch (IOException e) {
            log.severe(ExceptionUtils.getRootCauseMessage(e));
        }
        return fileAsString;
    }

    public static <T> InputStream readFileAsInputStream(final Class<T> clazz, final String fileName) {
        return clazz.getClassLoader().getResourceAsStream(META_INF_FOLDER + fileName);
    }
}
