package gll.pub.onepeas.config

import gll.pub.onepeas.model.OnePeasUser
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.http.MediaType

@Configuration
class RepositoryConfig : RepositoryRestConfigurerAdapter() {
    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
        config.setBasePath("/api")
        config.setDefaultMediaType(MediaType.APPLICATION_JSON)
        config.exposeIdsFor(OnePeasUser::class.java)
    }
}