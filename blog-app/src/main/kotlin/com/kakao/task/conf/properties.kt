package com.kakao.task.conf

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration
class AuthProperties(
    val env:Environment
){
    @Bean
    @Qualifier("kakaoAuth")
    fun getKakaoAuth(): String {
        return env.getProperty("kakao.auth") as String
    }

    @Bean
    @Qualifier("naverClientId")
    fun getNaverClientId(): String {
        return env.getProperty("naver.clientId") as String
    }

    @Bean
    @Qualifier("naverClientSecret")
    fun getNaverClientSecret(): String {
        return env.getProperty("naver.clientSecret") as String
    }
}