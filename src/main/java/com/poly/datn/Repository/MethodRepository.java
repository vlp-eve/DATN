package com.poly.datn.Repository;

import com.poly.datn.Entity.Payment.Method;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MethodRepository extends JpaRepository<Method, Long> {
}
