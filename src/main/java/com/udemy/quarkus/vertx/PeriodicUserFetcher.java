package com.udemy.quarkus.vertx;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;

@ApplicationScoped
public class PeriodicUserFetcher extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodicUserFetcher.class);

    @Override
    public Uni<Void> asyncStart() {
        vertx.periodicStream(Duration.ofSeconds(5).toMillis())
                .toMulti()
                .subscribe()
                .with(item -> {
                    LOG.info("Hello from PeriodicUserFetcher!");
                });
        return Uni.createFrom().voidItem();
    }
}
