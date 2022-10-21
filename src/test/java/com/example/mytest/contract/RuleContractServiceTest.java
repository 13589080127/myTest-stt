package com.example.mytest.contract;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mytest.mapper.ContractMapper;
import com.example.mytest.model.domain.Contract;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Fanjiyu
 * @Title:
 * @Description:
 * @date 2022/6/712:45
 */
@SpringBootTest
@Slf4j
public class RuleContractServiceTest {
    @Autowired
    private ContractMapper contractMapper;

//    String url = "http://qa-app-oa-lifangfang.ocjfuli.com/ruleenginetest/calculate_royalty";


//    String url = "https://oa.ocjfuli.com/ruleenginetest/calculate_royalty";
    String url = "https://oa.ocjfuli.com/ruleenginetest/calculate_performance";
    String ruleUrl = "http://10.8.101.250:20239/performance";
//    String ruleUrl = "http://10.8.101.250:20239/royalty";
    RestTemplate ruleRestTemplate = new RestTemplate();

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        LambdaQueryWrapper<Contract> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Contract::getId);
        List<Contract> list = contractMapper.selectList(lambdaQueryWrapper);
        for (Contract contract : list) {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("contractId", contract.getId());
            map.add("returnParam", true);
            //请求头的签名数据
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, header);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                log.info("结果：{}", exchange.getBody());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entitys = new HttpEntity<>(exchange.getBody(), headers);
                exchange = ruleRestTemplate.exchange(ruleUrl, HttpMethod.POST, entitys, String.class);
                log.info("规则结果：{}", exchange.getBody());

            } catch (Exception e) {
                log.info("异常：", e);
            }

        }
    }
}
