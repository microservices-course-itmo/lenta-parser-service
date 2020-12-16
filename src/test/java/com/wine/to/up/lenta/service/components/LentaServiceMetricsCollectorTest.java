package com.wine.to.up.lenta.service.components;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LentaServiceMetricsCollectorTest {

    @Test
    public void metricCollectorTest(){
        LentaServiceMetricsCollector lentaServiceMetricsCollector = new LentaServiceMetricsCollector();
        assertNotNull(lentaServiceMetricsCollector);

    }
}