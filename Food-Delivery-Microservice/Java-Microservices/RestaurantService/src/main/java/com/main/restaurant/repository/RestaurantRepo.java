package com.main.restaurant.repository;
import com.main.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Integer> {
   Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);
   Optional<Restaurant>findByEmail(String email);
   Page<Restaurant> findByAddress_CityContainingIgnoreCase(String city, Pageable pageable);
   Page<Restaurant>findByRatingGreaterThanEqual(Double rating,Pageable pageable);
   Page<Restaurant>findByAddress_CityContainingIgnoreCaseAndRatingGreaterThanEqual(String city,Double rating ,Pageable pageable);
   boolean existsByPhoneNumber(String phoneNumber);
//   List<Restaurants> findByCuisineTypesIgnoreCaseContaining(String cuisine);
   Page<Restaurant> findByCuisineTypesIgnoreCaseContaining(String cuisine,Pageable pageable);
}