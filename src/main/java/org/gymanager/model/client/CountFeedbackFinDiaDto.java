package org.gymanager.model.client;

import org.gymanager.model.domain.CountFeedbackFinDia;

import java.util.Date;

public record CountFeedbackFinDiaDto(Date fechaCarga, Long cantidad){

    public CountFeedbackFinDiaDto(CountFeedbackFinDia countFeedbackFinDia) {
        this(countFeedbackFinDia.getFechaCarga(), countFeedbackFinDia.getCantidad());
    }
}
