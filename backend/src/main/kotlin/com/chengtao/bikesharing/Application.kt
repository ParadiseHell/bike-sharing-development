package com.chengtao.bikesharing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class Application {
  @Bean
  fun corsConfigurer(): WebMvcConfigurer {
    return object : WebMvcConfigurer {
      override fun addCorsMappings(registry: CorsRegistry?) {
        registry?.addMapping("/*")?.allowedOrigins("*") ?: super.addCorsMappings(registry)
      }
    }
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}