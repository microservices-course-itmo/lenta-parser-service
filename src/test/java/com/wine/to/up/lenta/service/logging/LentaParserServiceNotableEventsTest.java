package com.wine.to.up.lenta.service.logging;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LentaParserServiceNotableEventsTest {

    @Test
    public void getNotableEvents(){
        assertNotNull(LentaParserServiceNotableEvents.E_BAD_REQUEST.getName());
        assertNotNull(LentaParserServiceNotableEvents.E_BAD_REQUEST.getTemplate());
    }

}