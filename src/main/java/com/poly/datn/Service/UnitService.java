package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Unit;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UnitService {
    List<Unit> getAllUnit();



    public Unit addUnit(Unit unit);



    public Unit updateUnit (Long id, Unit unitDetails);


    public void deleteUnit(Long id);
    public Unit  findUnitById(Long id);

    public List<Unit> getNonDeletedUnit();
}
