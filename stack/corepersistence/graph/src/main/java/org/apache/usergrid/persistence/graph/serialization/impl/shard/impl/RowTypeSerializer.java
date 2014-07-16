/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.apache.usergrid.persistence.graph.serialization.impl.shard.impl;


import org.apache.usergrid.persistence.core.astyanax.CompositeFieldSerializer;
import org.apache.usergrid.persistence.core.astyanax.IdRowCompositeSerializer;
import org.apache.usergrid.persistence.graph.serialization.impl.shard.RowKeyType;
import org.apache.usergrid.persistence.model.entity.Id;

import com.netflix.astyanax.model.CompositeBuilder;
import com.netflix.astyanax.model.CompositeParser;


public class RowTypeSerializer implements CompositeFieldSerializer<RowKeyType> {

    private static final IdRowCompositeSerializer ID_SER = IdRowCompositeSerializer.get();


    @Override
    public void toComposite( final CompositeBuilder builder, final RowKeyType keyType ) {

        //add the row id to the composite
        ID_SER.toComposite( builder, keyType.nodeId );

        builder.addLong( keyType.hash[0] );
        builder.addLong( keyType.hash[1] );
        builder.addLong( keyType.shardId );
    }


    @Override
    public RowKeyType fromComposite( final CompositeParser composite ) {

        final Id id = ID_SER.fromComposite( composite );
        final long[] hash = new long[] { composite.readLong(), composite.readLong() };
        final long shard = composite.readLong();

        return new RowKeyType( id, hash, shard );
    }
}
