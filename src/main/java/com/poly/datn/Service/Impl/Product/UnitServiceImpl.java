package com.poly.datn.Service.Impl.Product;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Unit;

import com.poly.datn.Repository.UnitRepository;
import com.poly.datn.Service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<Unit> getAllUnit() {
        return unitRepository.findByIsDeletedFalse();
    }

    @Override
    public Unit addUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public Unit updateUnit(Long id, Unit unitDetails) {
        try {
            Unit unit = unitRepository.findUnitById(id);
            if (unit == null) {
                throw new NoSuchElementException("Không tìm thấy Unit với ID: " + id);
            }unit.setName(unitDetails.getName());
            return unitRepository.save(unit);
        }catch (NoSuchElementException e) {
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }
    }


    public void deleteUnit(Long id) {
        try {
            Unit unit = unitRepository.findUnitById(id);
            if (unit == null) {
                throw new NoSuchElementException("Không tìm thấy Unit với ID: " + id);
            }
            unit.setIsDeleted(IsDelete.DELETED.getValue());
            unitRepository.save(unit);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }
    }


    @Override
    public Unit findUnitById(Long id) {
        return unitRepository.findUnitById(id);
    }

    @Override
    public List<Unit> getNonDeletedUnit() {
        return unitRepository.findByIsDeletedFalse();
    }
}
