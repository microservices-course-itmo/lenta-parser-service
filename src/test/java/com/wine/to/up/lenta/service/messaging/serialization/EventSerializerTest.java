package com.wine.to.up.lenta.service.messaging.serialization;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventSerializerTest {

    @Test
    public void getEventSerializer(){
        ParserApi.WineParsedEvent wineParsedEvent = ParserApi.WineParsedEvent.newBuilder().build();
        EventSerializer eventSerializer = new EventSerializer();
        assertNotNull(eventSerializer.serialize("topic",wineParsedEvent));
    }

}