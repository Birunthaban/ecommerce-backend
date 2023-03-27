package com.backend.ecommerce.repository;

import com.backend.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@EnableJpaRepositories
@Repository

public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query(value = "select * from Cart where id=:id", nativeQuery = true)
    static List<Cart> showcart(Integer userid) {
        return null;
    }

    //check the product
    @Query(value = "select quantity from Cart where id=?2 and product_id=?1", nativeQuery=true)
    Integer getExistingUnits(Integer product_id,Integer id);

    //add item
    @Transactional
    @Modifying
    @Query(value="INSERT INTO Cart (id, product_id,quantity)\n" +
            "VALUES (?3,?1,?2);", nativeQuery=true)
    void addToCart(Integer product_id, Integer units, Integer id);
    @Transactional
    @Modifying
    @Query(value = "update Cart set quantity=?2 where id=?3 and product_id=?1 ")
    void updateCart(Integer product_id, Integer units, Integer id);

    // Remove an item in the cart
    @Transactional
    @Modifying
    @Query(value = "delete from Cart where id=?2 and product_id=?1")
    void removeFromcart(Integer product_id,Integer id);


}
