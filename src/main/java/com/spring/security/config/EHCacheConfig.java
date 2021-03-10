package com.spring.security.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@EnableCaching
@Configuration
public class EHCacheConfig {
	@Bean
	public EhCacheCacheManager ehCacheCacheManager() {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehCacheManagerFactory().getObject());
		cacheManager.setTransactionAware(true);
		return cacheManager;
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactory() {
		EhCacheManagerFactoryBean ehCacheBean = new EhCacheManagerFactoryBean();
		ehCacheBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		ehCacheBean.setShared(true);
		return ehCacheBean;
	}
}
