package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
