package Liter_Alura.Literalura.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Desserealiza implements iDesserealiza{

    private ObjectMapper mapper = new ObjectMapper();
    ConsumoAPI apiConsumo = new ConsumoAPI();


    @Override
    public <T> T desserealizar(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}







