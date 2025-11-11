package rodrigo.cep.rest_template.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rodrigo.cep.rest_template.consultacep.CepResultDTO;

@Service
public class CepConsumer {

    private final CepProducer cepProducer;

    public CepConsumer(CepProducer cepProducer) {
        this.cepProducer = cepProducer;
    }

    @KafkaListener(topics = "cep-solicitacao", groupId = "cep-group")
    public void consumirCep(String cep) {
        System.out.println("📩 Recebido CEP: " + cep);

        RestTemplate rest = new RestTemplate();
        CepResultDTO resultado = rest.getForObject("https://viacep.com.br/ws/" + cep + "/json", CepResultDTO.class);

        cepProducer.enviarResultado(resultado);
    }
}
