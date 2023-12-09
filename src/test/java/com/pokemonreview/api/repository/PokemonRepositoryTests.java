package com.pokemonreview.api.repository;

import com.pokemonreview.api.containers.BaeldungPostgresqlContainer;
import com.pokemonreview.api.containers.MyPostgreSqlTest;
import com.pokemonreview.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

@MyPostgreSqlTest
class PokemonRepositoryTests {

    @ClassRule
    public static PostgreSQLContainer<BaeldungPostgresqlContainer> postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    void PokemonRepository_SaveAll_ReturnSavedPokemon() {

        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    void PokemonRepository_GetAll_ReturnMoreThenOnePokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findById(pokemon.getId()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findByType(pokemon.getType()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(pokemonReturn).isEmpty();
    }

    @Test
    @Sql(scripts = {"/sql/01-test.sql"})
    void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull_2() {

        Pokemon pokemonSave = pokemonRepository.findByType("electric").get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

}
