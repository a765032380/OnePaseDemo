package gll.pub.onepeas.repository

import gll.pub.onepeas.model.VideoEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@RepositoryRestResource(path = "/users", itemResourceRel = "/user")
interface VideoRepository : PagingAndSortingRepository<VideoEntity, Long> , JpaSpecificationExecutor<VideoEntity> {

    @Query(value = "SELECT * FROM ch_user chu WHERE chu.ch_user_id IN " +
            "(SELECT friend.receiver_user_id FROM ch_friend_entry friend WHERE friend.requester_user_id = :id)",
            nativeQuery = true)
    fun getFriendsById(@Param("id") id: Long) : List<VideoEntity>

}