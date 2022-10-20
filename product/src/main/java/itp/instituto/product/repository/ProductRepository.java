package itp.instituto.product.repository;

import itp.instituto.product.entity.Category;
import itp.instituto.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

  public List<Product> findByCategory(Category category);
   //Select * from tbl_prductos where category_id = $1;

}