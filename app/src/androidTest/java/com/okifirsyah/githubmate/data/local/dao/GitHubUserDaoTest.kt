package com.okifirsyah.githubmate.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.okifirsyah.githubmate.data.local.room.GitHubMateDatabase
import com.okifirsyah.githubmate.data.model.GitHubUser
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GitHubUserDaoTest {

    private lateinit var database: GitHubMateDatabase
    private lateinit var dao: GitHubUserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubMateDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.gitHubUserDao()
    }
    
    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getAuthorizedUser() = runTest {
        val user = GitHubUser(
            1,
            "okifirsyah",
            "url",
            "Jakarta",
            "Google",
            "bangkit",
            "bondowoso",
            40,
            40,
            isAuthorized = true
        )

        dao.insertauthorizedUser(
            user
        )

        val localUser = dao.getAuthorizedUser()

        assertEquals(user.username, localUser.username)
    }
}
