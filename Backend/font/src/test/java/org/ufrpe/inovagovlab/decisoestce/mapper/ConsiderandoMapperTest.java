package org.ufrpe.inovagovlab.decisoestce.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConsiderandoMapper.class})
@ExtendWith(SpringExtension.class)
public class ConsiderandoMapperTest {

    @Autowired
    private ConsiderandoMapper considerandoMapper;

    @Test
    public void formataTexto(){

    }
}
