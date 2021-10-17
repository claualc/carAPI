package com.carapi.domain;

import java.util.List;
import java.util.Optional;

import com.carapi.domain.dto.CarroDTO;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class CarroRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String GET_ALL = "SELECT * from carro";
    private final String INSERT = "INSERT INTO carro (NOME, TIPO) values (?,?)";
    private final String GET_BY_ID(Long id) {return "SELECT * FROM carro WHERE id="+id;};
    private final String GET_BY_TIPO(String tipo) {return "SELECT * FROM carro WHERE tipo='"+tipo+"'";};
    private final String UPDATE(Long id, String nome, String tipo) {return "UPDATE carro SET nome='"+nome+"', tipo='"+tipo+"' WHERE id="+id;};
    private final String DELETE(Long id) {return "DELETE FROM carro WHERE id="+id;};

    private RowMapper<Carro> rowMapper = (ResultSet rs, int rowNum) -> {
        Carro carro = new Carro();
        carro.setId(rs.getLong(1));
        carro.setNome(rs.getString(2));
        carro.setTipo(rs.getString(8));
        return carro;
    };

    public List<Carro> findAll() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }; 

    public List<Carro> findBytipo(String tipo){
        return jdbcTemplate.query(GET_BY_TIPO(tipo), rowMapper);
    };

    public Optional<Carro> findById(Long id){
        Carro c =  jdbcTemplate.queryForObject(GET_BY_ID(id),rowMapper);
        return Optional.of(c);
    };

    public Carro save(Carro carro) {
        int id =  jdbcTemplate.update(INSERT, carro.getNome(), carro.getTipo());
        return  jdbcTemplate.queryForObject(GET_BY_ID( Long.valueOf(id) ),rowMapper);
    };

    public Carro update(Long id, Carro carro) {
        jdbcTemplate.update(UPDATE(id, carro.getNome(), carro.getTipo()));
        System.out.println("ID ###########");
        System.out.println(id);
        return  jdbcTemplate.queryForObject(GET_BY_ID( id ),rowMapper);
    };

    public Boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE(id)) > 0;
    };
}
