package com.wine.to.up.lenta.service.services;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;

/**
 * Interface for methods which need to work with data base
 */
public interface DataBaseService {

    /**
     * Method that save object to data base
     *
     * @param productDTO - wine
     */
    void create(ProductDTO productDTO);
}
