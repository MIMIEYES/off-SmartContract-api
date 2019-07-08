package io.nuls.contract;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import io.nuls.contract.autoconfig.ApiModuleInitTool;
import io.nuls.contract.model.TempErrorResolver;
import io.nuls.core.log.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ApiModuleInitTool dataInitTool=contextRefreshedEvent.getApplicationContext().getBean(ApiModuleInitTool.class);
        try {
            Log.info("----------run Application Startup Listener-------------");
            dataInitTool.init();
            Map<String, JsonServiceExporter> beansOfType = contextRefreshedEvent.getApplicationContext().getBeansOfType(JsonServiceExporter.class);
            for(JsonServiceExporter bean : beansOfType.values()) {
                bean.setErrorResolver(TempErrorResolver.INSTANCE);
                bean.afterPropertiesSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
