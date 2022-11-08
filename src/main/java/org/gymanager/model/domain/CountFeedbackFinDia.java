package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "feedback_fin_dia_count")
public class CountFeedbackFinDia {
    @Id
    private Date fechaCarga;
    private Long cantidad;
}
