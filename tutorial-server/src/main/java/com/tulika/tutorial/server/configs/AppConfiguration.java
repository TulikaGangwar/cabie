package com.tulika.tutorial.server.configs;

import io.dropwizard.Configuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class AppConfiguration  extends Configuration {
}
