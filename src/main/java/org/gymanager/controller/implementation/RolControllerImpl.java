package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.RolController;
import org.gymanager.service.specification.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RolControllerImpl implements RolController {

    @NonNull
    private RolService rolService;

    @Override
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(rolService.getRoles());
    }
}
