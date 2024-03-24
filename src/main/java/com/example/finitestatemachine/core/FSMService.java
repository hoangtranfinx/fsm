package com.example.finitestatemachine.core;

import com.example.finitestatemachine.infra.config.Persist;
import com.example.finitestatemachine.infra.repository.dao.OriginationStateMachineDao;
import com.example.finitestatemachine.infra.repository.entity.OriginationStateMachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FSMService {
    @Qualifier("persist")
    private final Persist persist;

    @Qualifier("persistRetry")
    private final Persist persistRetry;

    private final OriginationStateMachineDao dao;

    public void genID(String data, int id) {
        OriginationStateMachineEntity ori =new OriginationStateMachineEntity(id, "INIT");
        dao.save(ori);
        persist.change(id,"GenID");
    }

    public void genIDRetry(String data, int id) {
        OriginationStateMachineEntity ori =new OriginationStateMachineEntity(id, "INIT");
        dao.save(ori);
        persistRetry.change(id,"GenID");
    }
}
