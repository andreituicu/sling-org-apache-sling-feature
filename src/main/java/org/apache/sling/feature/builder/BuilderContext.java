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
package org.apache.sling.feature.builder;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.sling.feature.KeyValueMap;

/**
 * Builder context holds services used by  {@link FeatureBuilder}.
 */
public class BuilderContext {

    private final FeatureProvider provider;

    private final List<FeatureExtensionHandler> featureExtensionHandlers = new CopyOnWriteArrayList<>();
    private final KeyValueMap variables = new KeyValueMap();
    private final Map<String, String> properties = new LinkedHashMap<>();

    /**
     * Create a new context
     *
     * @param provider A provider providing the included features
     * @throws IllegalArgumentException If feature provider is {@code null}
     */
    public BuilderContext(final FeatureProvider provider) {
        this(provider, null, null);
    }

    /**
     * Create a new context
     *
     * @param provider A provider providing the included features
     * @param variables A map of variables to override on feature merge
     * @param properties A map of framework properties to override on feature merge
     * @throws IllegalArgumentException If feature provider is {@code null}
     */
    public BuilderContext(final FeatureProvider provider, KeyValueMap variables, Map<String, String> properties) {
        if (variables != null) {
            this.variables.putAll(variables);
        }
        if (properties != null) {
            this.properties.putAll(properties);
        }
        if ( provider == null ) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        this.provider = provider;
    }

    /**
     * Add a feature extension handlers
     * @param handlers Handler(s) to add
     * @return This instance
     */
    public BuilderContext add(final FeatureExtensionHandler... handlers) {
        featureExtensionHandlers.addAll(Arrays.asList(handlers));
        return this;
    }

    KeyValueMap getVariables() {
        return  this.variables;
    }

    Map<String, String> getProperties() {
        return this.properties;
    }
    /**
     * Get the feature provider.
     * @return The feature provider
     */
    FeatureProvider getFeatureProvider() {
        return this.provider;
    }

    /**
     * Get the list of extension handlers
     * @return The list of handlers
     */
    List<FeatureExtensionHandler> getFeatureExtensionHandlers() {
        return this.featureExtensionHandlers;
    }

    /**
     * Clone the context and replace the feature provider
     * @param featureProvider The new feature provider
     * @return Cloned context
     */
    BuilderContext clone(final FeatureProvider featureProvider) {
        final BuilderContext ctx = new BuilderContext(featureProvider, this.variables, this.properties);
        ctx.featureExtensionHandlers.addAll(featureExtensionHandlers);
        return ctx;
    }
}
