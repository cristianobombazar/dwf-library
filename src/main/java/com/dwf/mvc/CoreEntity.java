package com.dwf.mvc;

import java.time.LocalDateTime;

public interface CoreEntity<ID> {
    ID getId();
    LocalDateTime getUltimaAlteracao();
    void setUltimaAlteracao(LocalDateTime localDateTime);
    void preSave();
}
