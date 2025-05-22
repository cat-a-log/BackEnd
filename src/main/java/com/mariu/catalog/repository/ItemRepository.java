package com.mariu.catalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.Item;
import com.mariu.catalog.model.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
  Page<Item> findByBox(Box box, Pageable paging);

  Optional<Item> findByIdAndBox_CreatedBy(Long id, User user);
}