package edu.iu.habahram.ducksservice.repository;

import edu.iu.habahram.ducksservice.model.Ducks;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DucksRepository extends CrudRepository<Ducks, String> {
    Ducks findByUsername(String username);
}
