package com.pauloneto.batchapplication.job.itemwriter;

import com.pauloneto.batchapplication.models.CotaParlamentar;
import com.pauloneto.batchapplication.repository.nosql.CotaParlamentarRepository;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Dependent
@Named("itemWriterCotaParlamentar")
public class ItemWriterCotaParlamentar extends AbstractItemWriter {

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private CotaParlamentarRepository repository;

    @Override
    public void writeItems(List<Object> list) throws Exception {
        Optional<List<Object>> optionalList = Optional.ofNullable(list);
        optionalList.ifPresent( objects -> {
            for (Object obj : objects) {
                CotaParlamentar cp = (CotaParlamentar) obj;
                repository.save(cp);
            }
        });
    }
}
