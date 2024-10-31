package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Unit;
import com.poly.datn.Repository.UnitRepository;
import com.poly.datn.Service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<Unit> getAllUnit() {
        return unitRepository.findByIsDeleteFalse();
    }

    @Override
    public Unit addUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public Unit updateUnit(Long id, Unit unitDetails) {
        Unit unit = unitRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy"));
        unit.setName(unitDetails.getName());
        return unitRepository.save(unit);
    }

    @Override
    public void deleteUnit(Long id) {
        Unit unit = unitRepository.findUnitById(id);
        unit.setIsDelete(true);
        unitRepository.save(unit);

    }

    @Override
    public Unit findUnitById(Long id) {
        return unitRepository.findUnitById(id);
    }

    @Override
    public List<Unit> getNonDeletedUnit() {
        return unitRepository.findByIsDeleteFalse();
    }
}
