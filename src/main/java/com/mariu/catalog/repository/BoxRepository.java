package com.mariu.catalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mariu.catalog.model.Box;
import com.mariu.catalog.model.User;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long>, JpaSpecificationExecutor<Box> {
    Page<Box> findByCreatedBy(User user, Pageable paging);
}
