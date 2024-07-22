/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.kafka.deployment;

import java.util.List;
import java.util.Set;

import io.quarkus.builder.Version;
import io.quarkus.maven.dependency.Dependency;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaClientFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuarkusKafkaClientFactoryDisabledMergeConfigTest {

    @RegisterExtension
    static final QuarkusUnitTest CONFIG = new QuarkusUnitTest()
            .setForcedDependencies(List.of(
                    Dependency.of("io.quarkus", "quarkus-kubernetes-service-binding", Version.getVersion())))
            .withConfigurationResource("application-configuration-merging-disabled.properties")
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Inject
    CamelContext context;

    @Test
    public void quarkusKafkaClientFactoryRegistryBeanNull() {
        Set<KafkaClientFactory> factories = context.getRegistry().findByType(KafkaClientFactory.class);
        assertTrue(factories.isEmpty());
    }
}
