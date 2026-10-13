package com.rd.autopecas.erp_autopecas.domain.role;

import com.rd.autopecas.erp_autopecas.domain.role.dto.RoleRequest;
import com.rd.autopecas.erp_autopecas.domain.role.dto.RoleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("{id}")
    public ResponseEntity<RoleResponse> findByid(@PathVariable Long id){
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll(){
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest roleRequest){
        return ResponseEntity.ok(roleService.create(roleRequest));
    }
}
