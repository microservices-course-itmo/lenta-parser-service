package com.wine.to.up.lenta.service.parser;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;

import java.util.List;

/**
 * Class that converting data to Java data type
 */
public interface LentaWineParserService {

    /**
     * This method convert JSON objects to Java objects "ProductDTO"
     *
     * @param wineList - List of JSON objects that parse from site
     * @return List of ProductDTO
     */
    List<ProductDTO> parseWineList(ParserRspImpl wineList);
}
