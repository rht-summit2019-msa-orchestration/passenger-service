/*
 * Copyright 2017-2019 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.acme.ride.passenger.tracing;


import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map.Entry;

import io.opentracing.propagation.TextMap;
import org.apache.kafka.common.header.Headers;

public class KafkaHeadersInjectAdapter implements TextMap {

    private final Headers headers;
    private final boolean second;

    KafkaHeadersInjectAdapter(Headers headers, boolean second) {
        this.headers = headers;
        this.second = second;
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        throw new UnsupportedOperationException("iterator should never be used with Tracer.inject()");
    }

    @Override
    public void put(String key, String value) {
        if (second) {
            headers.add("second_span_" + key, value.getBytes(StandardCharsets.UTF_8));
        } else {
            headers.add(key, value.getBytes(StandardCharsets.UTF_8));
        }
    }
}
