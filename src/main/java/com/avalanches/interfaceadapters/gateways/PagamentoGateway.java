package com.avalanches.interfaceadapters.gateways;

import com.avalanches.enterprisebusinessrules.entities.Pagamento;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.inject.Inject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;

public class PagamentoGateway implements PagamentoGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoGateway.class);

    private final RedisCommands<String, String> redisCommands;
    private final WebHookMockParams webHookMockParams;

    public PagamentoGateway(BancoDeDadosContextoInterface bancoDeDadosContexto,
                            WebHookMockParams webHookMockParams) {
        this.redisCommands = bancoDeDadosContexto.getRedisCommands();
        this.webHookMockParams = webHookMockParams;
    }

    @Override
    public void cadastrar(Pagamento pagamento) {
        String redisKey = "pagamento:" + pagamento.getIdPedido();
        redisCommands.hset(redisKey, "status", pagamento.getStatusPagamento().name());
    }

    @Override
    public Boolean efetuarPagamento(Integer idPedido) {
        String url = webHookMockParams.getBaseUrl() + idPedido;
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        logger.info(url);

        RequestBody body = RequestBody.create("", JSON);
        logger.info(body.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = webHookMockParams.getHttpClient().newCall(request).execute()) {
            logger.info(response.body() != null ? response.body().toString() : null);
            return response.isSuccessful();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    @Override
    public StatusPagamento consultaStatus(Integer idPedido) {
        String redisKey = "pagamento:" + idPedido;
        String statusString = redisCommands.hget(redisKey, "status");
        if (statusString != null) {
            return StatusPagamento.valueOf(statusString);
        } else {
            return null;
        }
    }

    @Override
    public boolean verificaPagamentoExiste(Integer idPedido) {
        String redisKey = "pagamento:" + idPedido;
        Long exists = redisCommands.exists(redisKey);
        return exists != null && exists > 0; // If exists is 1, return true; if 0, return false
    }

}
