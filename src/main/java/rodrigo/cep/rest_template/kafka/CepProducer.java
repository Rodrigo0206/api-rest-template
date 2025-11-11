package rodrigo.cep.rest_template.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rodrigo.cep.rest_template.consultacep.CepResultDTO;

@Service
public class CepProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public CepProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarResultado(CepResultDTO dto) {
        try {
            String json = mapper.writeValueAsString(dto);
            kafkaTemplate.send("cep-resposta", json);
            System.out.println("✅ Resultado enviado ao Kafka: " + dto.getCep());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
