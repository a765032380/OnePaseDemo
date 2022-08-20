package gll.pub.onepeas.eventHandlers

import gll.pub.onepeas.model.OnePeasUser
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.stereotype.Component

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
@RepositoryEventHandler
class OnePeasEventHandler {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @HandleBeforeCreate(value = [OnePeasUser::class])
    fun handleBeforeCreate (user: OnePeasUser) {
        log.info("Saving (New User) ${user.getFullName()} to Database.")
    }

}