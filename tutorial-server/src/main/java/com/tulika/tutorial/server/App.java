package com.tulika.tutorial.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Stage;
import com.tulika.tutorial.server.configs.AppConfiguration;
import com.tulika.tutorial.server.modules.ConfigurationModule;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jmxmp.JmxMpBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import ru.vyarus.dropwizard.guice.GuiceBundle;

@Slf4j
public class App extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception{
        new App().run(args);
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception {
        log.info("Service Configuration = {}", appConfiguration);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {

        // bootsrap is a library loading of all the bundles and static configurations(yml file) needed for startups
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()));
        GuiceBundle<AppConfiguration> guiceBundle = GuiceBundle.<AppConfiguration>builder() //dependency injection
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(
                        new ConfigurationModule()
                )
                .build(Stage.PRODUCTION);

        bootstrap.addBundle(guiceBundle);
//        bootstrap.addBundle(new JmxMpBundle() {
//            protected int port() {
//                return 9010;
//            }
//        });
        ObjectMapper mapper = bootstrap.getObjectMapper(); //object mapper comes from jackson library
        setMapperProperties(mapper);
    }

    private void setMapperProperties(ObjectMapper mapper) {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
    }


}
