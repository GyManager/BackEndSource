package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.DashboardController;
import org.gymanager.model.client.ClientsSummary;
import org.gymanager.service.specification.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardControllerImpl implements DashboardController {

    @NonNull
    private DashboardService dashboardService;

    @Override
    public ResponseEntity<ClientsSummary> getSummary(Long dayCountVencimientoMatricula, Long dayOverdueVencimientoMatricula) {
        return ResponseEntity.ok(dashboardService.getSummary(dayCountVencimientoMatricula, dayOverdueVencimientoMatricula));
    }
}
