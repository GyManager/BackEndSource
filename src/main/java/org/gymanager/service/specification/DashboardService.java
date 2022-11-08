package org.gymanager.service.specification;

import org.gymanager.model.client.ClientsSummary;

public interface DashboardService {

    ClientsSummary getSummary(Long dayCountVencimientoMatricula,
                              Long dayOverdueVencimientoMatricula,
                              Long dayCountSinFinalizarDia,
                              Long dayCountSeguimientoFinDiaEstado,
                              Long dayCountSeguimientoFinDiaFecha);
}
