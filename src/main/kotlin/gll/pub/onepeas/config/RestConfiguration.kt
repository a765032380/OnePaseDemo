package gll.pub.onepeas.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.core.convert.ConversionService
import org.springframework.beans.factory.ObjectFactory


@Configuration
class RestConfiguration(context: ApplicationContext, conversionService: ObjectFactory<ConversionService>) :
        RepositoryRestMvcConfiguration(context, conversionService)