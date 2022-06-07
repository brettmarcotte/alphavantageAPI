package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRepository extends CrudRepository<Overview, Long> {

    Optional<Overview> findBySymbol(String symbol);

    Optional<Overview> findByAssetType(String assetType);

    Optional<Overview> findByName(String name);

    List<Overview> findByExchange(String exchange);

    List<Overview> findByCurrency(String currency);

    List<Overview> findByCountry(String country);

    List<Overview> findBySector(String Sector);


    
}
