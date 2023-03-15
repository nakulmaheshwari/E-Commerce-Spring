package com.nakul.ecommerce.eweb_backend.entities.dao;

import com.nakul.ecommerce.eweb_backend.entities.LocalUser;
import com.nakul.ecommerce.eweb_backend.entities.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

/**
 * Data Access Object to access WebOrder data.
 */
public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {

    List<WebOrder> findByUser(LocalUser user);

}