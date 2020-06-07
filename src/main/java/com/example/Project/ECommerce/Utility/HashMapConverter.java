package com.example.Project.ECommerce.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String,Object>,String> {

    private final ObjectMapper objectMapper=new ObjectMapper();
    private static final Logger LOGGER= LoggerFactory.getLogger(HashMapConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {
        String customerInfoJson=null;
        try{
            customerInfoJson=objectMapper.writeValueAsString(customerInfo);
        }

        catch (final JsonProcessingException e){
            LOGGER.debug("Json writing error{}",e);
        }
        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJson) {
        Map<String,Object>customerInfo=null;
        try {
            customerInfo=objectMapper.readValue(customerInfoJson,Map.class);
        }
        catch (final IOException ex){
            LOGGER.debug("Json reading error{} ",ex);
        }
        return customerInfo;
    }
}