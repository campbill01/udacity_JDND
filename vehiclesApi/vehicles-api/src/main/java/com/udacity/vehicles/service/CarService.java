package com.udacity.vehicles.service;

import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Implements the car service create, read, update or delete information about
 * vehicles, as well as gather related location and price data when desired.
 */

@Service
public class CarService {
    private final static Logger logger = Logger.getLogger(CarService.class.getName());
    private final CarRepository repository;
    @Autowired
    WebClient maps;

    @Autowired
    WebClient pricing;

    @Autowired
    ModelMapper modelMapper;

    public CarService(CarRepository repository, WebClient maps, WebClient pricing) {
        this.maps = maps;
        this.pricing = pricing;
        this.repository = repository;
        logger.info("########## In car service");
    }

    /**
     * Gathers a list of all vehicles
     * 
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        logger.info("###### In List car method  ##########");
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car;
        Optional<Car> optionalCar = repository.findById(id);
        if(optionalCar.isPresent()){
           car = optionalCar.get();
        } else{
            throw new CarNotFoundException();
        }
        
        PriceClient priceClient = new PriceClient(pricing);
        car.setPrice(priceClient.getPrice(id));

        Location carLocation = car.getLocation();
        MapsClient mapsClient = new MapsClient(maps, modelMapper);
        car.setLocation(mapsClient.getAddress(carLocation));

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car;
        Optional<Car> optionalCar = repository.findById(id);
        if(optionalCar.isPresent()){
           car = optionalCar.get();
        } else{
            throw new CarNotFoundException();
        }

        repository.delete(car);

    }
}
