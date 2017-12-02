package com.getbase.services

import com.getbase.models.User
import spock.lang.Shared

class UsersServiceTest extends BaseSpecification {

    @Shared def user = user ?: client.users().self()

    def "List - with params"() {
        when:
        def users = client.users().list(["page": 1, "per_page": 1])

        then:
        users.size() > 0
    }

    def "List - with query param builder"() {
        when:
        def users = client.users().list(new UsersService.SearchCriteria().page(1).perPage(1))

        then:
        users.size() > 0
    }

    def "List - by ids"() {
        when:
        def users = client.users().list(new UsersService.SearchCriteria().ids([user.id]))

        then:
        users.size() == 1
        users*.id == [user.id]
    }

    def "Get"() {
        given:
        def searched = user

        when:
        def found = client.users().get(searched.id)

        then:
        found instanceof User
        found.id == searched.id
    }

    def "Self"() {
        when:
        def user = client.users().self()

        then:
        user instanceof User
        user.id > 0
    }

}
