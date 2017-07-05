package me.musclegeeker.demo.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MuscleGeeker on 2017/6/30.
 */
@Configuration
public class DruidConfiguration implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    private Environment environment;

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    /**
     * 注册druidDataSource
     *
     * @Author 梁红健(lianghongjian@git.com.cn)
     * @Date 2017/6/30 13:54
     */
    @Bean(name = "druidDataSource")
    @Primary
    public DruidDataSource druidDataSource() throws SQLException {
        if (StringUtils.isEmpty(propertyResolver.getProperty("url"))) {
            System.out.println("Your database connection pool configuration is incorrect," +
                    " please check your spring profile, current profiles are:"
                    + Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        dataSource.setUsername(propertyResolver.getProperty("username"));
        dataSource.setPassword(propertyResolver.getProperty("password"));
        dataSource.setUrl(propertyResolver.getProperty("url"));

        dataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        dataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        dataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));

        dataSource.setMaxWait(Long.parseLong(propertyResolver.getProperty("maxWait")));

        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        dataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));

        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementsPerConnectionSize")));

        dataSource.setFilters(propertyResolver.getProperty("filters"));
        return dataSource;
    }

    /**
     * ************** 打开Druid监控空能 **************
     */
    @Bean
    @Primary
    public ServletRegistrationBean servletRegistrationBean() {
        logger.info("Init Druid Servlet Configuration.");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "admin");
        initParameters.put("resetEnable", "false");
        initParameters.put("allow", ""); // 白名单，若为空则允许所有访问
//        initParameters.put("deny", "192.168.1.1"); // 黑名单，若与白名单存在一样的值时，黑名单优先级高
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    @Primary
    public FilterRegistrationBean filterRegistrationBean() {
        logger.info("Init Druid Filter Configuration.");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
