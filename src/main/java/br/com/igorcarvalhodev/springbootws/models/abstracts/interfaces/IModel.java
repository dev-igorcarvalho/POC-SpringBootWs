package br.com.igorcarvalhodev.springbootws.models.abstracts.interfaces;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface IModel extends Serializable {

    Long getId();

    void setId(Long id);

}
