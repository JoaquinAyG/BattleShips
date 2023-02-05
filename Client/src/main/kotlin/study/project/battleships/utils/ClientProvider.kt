package study.project.battleships.utils

import study.project.battleships.models.User
import study.project.battleships.service.Client

object ClientProvider {
    lateinit var client: Client
    fun get(): Client {
        return client
    }

    fun set(client: Client) {
        this.client = client
    }
}