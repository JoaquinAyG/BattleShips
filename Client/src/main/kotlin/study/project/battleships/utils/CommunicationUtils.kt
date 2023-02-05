package study.project.battleships.utils

object CommunicationUtils {
    const val DEFAULT_SERVER_PORT = 8080
    const val DEFAULT_SERVER_HOST = "localhost"
    const val SEND_POSITION = 0
    const val SEND_BOARD = 1
    const val SEND_NAME = 2
    const val SEND_SLOT_TYPE = 6
    const val RECEIVE_POSITION = 3
    const val RECEIVE_SLOT_TYPE = 4
    const val RECEIVE_NAME = 5
    const val RECEIVE_TURN = 7
    const val END = 8
}