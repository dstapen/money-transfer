package com.github.dstapen.acme.processing;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class App {
    private static final Logger LOG = getLogger(App.class);

    public static void main(String[] args) {
        Micronaut.run(App.class);
    }
}
