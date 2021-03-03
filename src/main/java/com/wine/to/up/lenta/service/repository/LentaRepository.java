package com.wine.to.up.lenta.service.repository;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LentaRepository extends JpaRepository<ProductDTO, Integer> {
}