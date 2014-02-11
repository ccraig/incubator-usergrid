package org.apache.usergrid.persistence.collection.mvcc.entity.impl;


import java.util.UUID;

import org.junit.Test;

import org.apache.usergrid.persistence.collection.mvcc.entity.MvccEntity;
import org.apache.usergrid.persistence.model.entity.Entity;
import org.apache.usergrid.persistence.model.entity.SimpleId;
import org.apache.usergrid.persistence.model.util.UUIDGenerator;

import com.google.common.base.Optional;

import static org.junit.Assert.assertEquals;


/** @author tnine */
public class MvccEntityImplTest {


    @Test(expected = NullPointerException.class)
    public void entityIdRequired() {

        new MvccEntityImpl( null, UUIDGenerator.newTimeUUID(),  MvccEntity.Status.COMPLETE,Optional.of( new Entity() ) );
    }


    @Test(expected = NullPointerException.class)
    public void versionRequired() {

        new MvccEntityImpl( new SimpleId( "test" ), null,  MvccEntity.Status.COMPLETE, Optional.of( new Entity() ) );
    }


    @Test(expected = NullPointerException.class)
    public void entityRequired() {

        new MvccEntityImpl( new SimpleId( "test" ), UUIDGenerator.newTimeUUID(), MvccEntity.Status.COMPLETE, ( Entity ) null );
    }


    @Test(expected = NullPointerException.class)
    public void optionalRequired() {

        new MvccEntityImpl( new SimpleId( "test" ), UUIDGenerator.newTimeUUID(), MvccEntity.Status.COMPLETE, ( Optional ) null );
    }


    @Test(expected = NullPointerException.class)
    public void statusRequired() {

        new MvccEntityImpl( new SimpleId( "test" ), UUIDGenerator.newTimeUUID(), null, ( Entity ) null );
    }


    @Test
    public void correctValueEntity() {

        final SimpleId entityId = new SimpleId( "test" );
        final UUID version = UUIDGenerator.newTimeUUID();
        final Entity entity = new Entity( entityId );

        MvccEntityImpl logEntry = new MvccEntityImpl( entityId, version, MvccEntity.Status.COMPLETE, entity );

        assertEquals( entityId, logEntry.getId() );
        assertEquals( version, logEntry.getVersion() );
        assertEquals( entity, logEntry.getEntity().get() );
    }


    @Test
    public void correctValueOptional() {

        final SimpleId entityId = new SimpleId( "test" );
        final UUID version = UUIDGenerator.newTimeUUID();
        final Entity entity = new Entity( entityId );

        MvccEntityImpl logEntry = new MvccEntityImpl( entityId, version,  MvccEntity.Status.COMPLETE,Optional.of( entity ) );

        assertEquals( entityId, logEntry.getId() );
        assertEquals( version, logEntry.getVersion() );
        assertEquals( entity, logEntry.getEntity().get() );
    }


    @Test
    public void equals() {

        final SimpleId entityId = new SimpleId( "test" );
        final UUID version = UUIDGenerator.newTimeUUID();
        final Entity entity = new Entity( entityId );

        MvccEntityImpl first = new MvccEntityImpl( entityId, version,  MvccEntity.Status.COMPLETE, Optional.of( entity ) );

        MvccEntityImpl second = new MvccEntityImpl( entityId, version, MvccEntity.Status.COMPLETE, Optional.of( entity ) );

        assertEquals( first, second );
    }


    @Test
    public void testHashCode() {

        final SimpleId entityId = new SimpleId( "test" );
        final UUID version = UUIDGenerator.newTimeUUID();
        final Entity entity = new Entity( entityId );

        MvccEntityImpl first = new MvccEntityImpl( entityId, version,  MvccEntity.Status.COMPLETE,Optional.of( entity ) );

        MvccEntityImpl second = new MvccEntityImpl( entityId, version, MvccEntity.Status.COMPLETE, Optional.of( entity ) );

        assertEquals( first.hashCode(), second.hashCode() );
    }
}
