package com.wine.to.up.lenta.service.db.constans;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import org.junit.Assert;
import org.junit.Test;

public class SugarTest {
    @Test
    public void sugarDryTest() {
        Assert.assertEquals(ParserApi.Wine.Sugar.DRY, Sugar.resolve("Сухое"));
    }

    @Test
    public void sugarSweetTest() {
        Assert.assertEquals(ParserApi.Wine.Sugar.SWEET, Sugar.resolve("Сладкое"));
    }

    @Test
    public void sugarMediumDryTest(){
        Assert.assertEquals(ParserApi.Wine.Sugar.MEDIUM_DRY, Sugar.resolve("Полусухое"));
    }

    @Test
    public void sugarMediumTest(){
        Assert.assertEquals(ParserApi.Wine.Sugar.MEDIUM, Sugar.resolve("Полусладкое"));
    }
}