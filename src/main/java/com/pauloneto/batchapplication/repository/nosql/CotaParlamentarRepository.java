package com.pauloneto.batchapplication.repository.nosql;

import com.pauloneto.batchapplication.models.CotaParlamentar;
import org.jnosql.artemis.Repository;

import java.util.List;
import java.util.stream.Stream;

public interface CotaParlamentarRepository extends Repository<CotaParlamentar,String> {

    public List<CotaParlamentar> findAll();

    public Stream<CotaParlamentar> findByCpf(String cpf);
}
