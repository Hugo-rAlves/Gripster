package org.ufrpe.inovagovlab.decisoestce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Local {

    @Id
    private int codMunicipio;
    private String nomeMunicipio;

}
