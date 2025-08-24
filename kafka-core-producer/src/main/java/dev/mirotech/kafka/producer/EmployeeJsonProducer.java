package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Employee employee) {
        //convert the employee to Json string and publish it to topic t-employee
        try {
            String employeeJson = objectMapper.writeValueAsString(employee);
            kafkaTemplate.send("t-employee", employeeJson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
