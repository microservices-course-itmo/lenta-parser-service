package com.wine.to.up.lenta.service.services.impl;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.repository.LentaRepository;
import com.wine.to.up.lenta.service.services.DataBaseService;

/**
 * Class that implements DataBaseService interface
 */
public class DataBaseServiceImpl implements DataBaseService {

    @Autowired
    private LentaRepository lentaRepository;

    @Override
    public void create(ProductDTO productDTO) {
        lentaRepository.save(productDTO);
    }
}
