/*
 * Copyright (C) 2017 Thomas Traude
 * Copyright (C) 2017 Daniel Watt
 * Copyright (C) 2018 Nils Petzaell
 *
 * This file is part of SchemaSpy.
 *
 * SchemaSpy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SchemaSpy. If not, see <http://www.gnu.org/licenses/>.
 */
package io.xmeta.screw;

import com.beust.jcommander.IDefaultProvider;
import io.xmeta.screw.cli.CommandLineArgumentParser;
import io.xmeta.screw.cli.CommandLineArguments;
import io.xmeta.screw.cli.ConfigFileArgumentParser;
import io.xmeta.screw.cli.DefaultProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Thomas Traude
 * @author Daniel Watt
 * @author Nils Petzaell
 */
@Configuration
@ComponentScan(basePackages = {"io.xmeta.screw"})
public class SchemaSpyConfiguration {

    @Autowired
    private ConfigFileArgumentParser configFileArgumentParser;

    @Autowired
    private DefaultProviderFactory factory;

    @Bean
    public CommandLineArguments commandLineArguments() {
        return new CommandLineArguments();
    }

    @Bean
    public CommandLineArgumentParser commandLineArgumentParser(ApplicationArguments applicationArguments, CommandLineArguments commandLineArguments) {
        Objects.requireNonNull(applicationArguments);

        String[] args = applicationArguments.getSourceArgs();

        Objects.requireNonNull(args);
        IDefaultProvider iDefaultProvider = findDefaultProvider(args);
        return new CommandLineArgumentParser(commandLineArguments, iDefaultProvider);
    }

    private IDefaultProvider findDefaultProvider(String... args) {
        Optional<String> configFileName = configFileArgumentParser.parseConfigFileArgumentValue(args);
        return factory.create(configFileName.orElse(null));
    }

}
