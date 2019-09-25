package com.udacity.pricing.domain.price;

import com.udacity.pricing.domain.price.Price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// @Repository
// public class PriceRepository {

// // }
// @RepositoryRestResource(collectionResourceRel = "vehicle", path="vehicle")
// public interface PriceRepository extends PagingAndSortingRepository<Price, Long>{
// //public interface PriceRepository extends CrudRepository <Price, Long> {
//     List<Price> findByName(@Param("vehicleId") Long vehicleId);
public interface PriceRepository extends CrudRepository<Price, Long> {
	//Price getPrice(Long vehicleId);
	//Price findById(@Param("vehicleId") Long vehicleId);
	Price findByvehicleId(@Param("vehicleId") Long vehicleId);
}