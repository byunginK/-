package com.example.reserve.demo.lock

import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.function.Supplier
import javax.sql.DataSource

class UserLevelLockFinal(private val dataSource: DataSource) {
    fun <T> executeWithLock(
            userLockName: String, timeoutSeconds: Int, supplier: Supplier<T>
    ): T {
        try {
            dataSource.connection.use { connection ->
                return try {
                    log.info("start getLock=[{}], timeoutSeconds : [{}], connection=[{}]",
                             userLockName,
                             timeoutSeconds,
                             connection)
                    getLock(connection, userLockName, timeoutSeconds)
                    log.info("success getLock=[{}], timeoutSeconds : [{}], connection=[{}]",
                             userLockName,
                             timeoutSeconds,
                             connection)
                    supplier.get()
                } finally {
                    log.info("start releaseLock=[{}], connection=[{}]", userLockName, connection)
                    releaseLock(connection, userLockName)
                    log.info("success releaseLock=[{}], connection=[{}]", userLockName, connection)
                }
            }
        } catch (e: RuntimeException) {
            throw RuntimeException(e.message, e)
        } catch (e: SQLException) {
            throw RuntimeException(e.message, e)
        }
    }

    @Throws(SQLException::class)
    private fun getLock(
            connection: Connection, userLockName: String, timeoutseconds: Int
    ) {
        connection.prepareStatement(GET_LOCK).use { preparedStatement ->
            preparedStatement.setString(1, userLockName)
            preparedStatement.setInt(2, timeoutseconds)
            checkResultSet(userLockName, preparedStatement, "GetLock_")
        }
    }

    @Throws(SQLException::class)
    private fun releaseLock(
            connection: Connection, userLockName: String
    ) {
        connection.prepareStatement(RELEASE_LOCK).use { preparedStatement ->
            preparedStatement.setString(1, userLockName)
            checkResultSet(userLockName, preparedStatement, "ReleaseLock_")
        }
    }

    @Throws(SQLException::class)
    private fun checkResultSet(
            userLockName: String, preparedStatement: PreparedStatement, type: String
    ) {
        preparedStatement.executeQuery().use { resultSet ->
            if (!resultSet.next()) {
                log.error("USER LEVEL LOCK 쿼리 결과 값이 없습니다. type = [{}], userLockName : [{}], connection=[{}]",
                          type,
                          userLockName,
                          preparedStatement.connection)
                throw RuntimeException(EXCEPTION_MESSAGE)
            }
            val result = resultSet.getInt(1)
            if (result != 1) {
                log.error(
                        "USER LEVEL LOCK 쿼리 결과 값이 1이 아닙니다. type = [{}], result : [{}] userLockName : [{}], connection=[{}]",
                        type,
                        result,
                        userLockName,
                        preparedStatement.connection)
                throw RuntimeException(EXCEPTION_MESSAGE)
            }
        }
    }

    companion object {
        private const val GET_LOCK = "SELECT GET_LOCK(?, ?)"
        private const val RELEASE_LOCK = "SELECT RELEASE_LOCK(?)"
        private const val EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다."
        private val log = LoggerFactory.getLogger(UserLevelLockFinal::class.java)
    }
}