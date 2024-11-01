package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Product.Unit;

import com.poly.datn.Service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitRestController {

    @Autowired
    private UnitService unitService;


    @GetMapping
    public List<Unit> getAllUnits() {
        return unitService.getNonDeletedUnit();
    }


    @PostMapping
    public ResponseEntity<Unit> addUnit(@RequestBody Unit unit) {
        Unit savedUnit = unitService.addUnit(unit);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable Long id, @RequestBody Unit unitDetails) {
        Unit updatedUnit = unitService.updateUnit(id, unitDetails);
        return ResponseEntity.ok(updatedUnit);
    }

    // API để xóa đơn vị
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.ok("Đơn vị đã được xóa!");
    }
}
