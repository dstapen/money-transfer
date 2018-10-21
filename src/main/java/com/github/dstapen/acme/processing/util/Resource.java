package com.github.dstapen.acme.processing.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.google.common.base.Throwables.throwIfUnchecked;
import static org.slf4j.LoggerFactory.getLogger;

public final class Resource {
    private static final Logger LOG = getLogger(Resource.class);

    @Nonnull
    public static final String getResource(String aResourceLocation) {
        try {
            return CharStreams.toString(new InputStreamReader(Resource.class.getResourceAsStream(aResourceLocation), Charsets.UTF_8));
        } catch (IOException ioe) {
            LOG.error("Cannot load {}", aResourceLocation, ioe);
            throwIfUnchecked(ioe);
            throw new RuntimeException(ioe);
        }
    }

    private Resource(){}

}
