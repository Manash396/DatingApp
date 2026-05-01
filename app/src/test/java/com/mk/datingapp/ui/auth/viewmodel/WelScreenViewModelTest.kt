package com.mk.datingapp.ui.auth.viewmodel

import com.mk.datingapp.domain.model.AuthUser
import com.mk.datingapp.domain.repository.AnalyticsRepository
import com.mk.datingapp.domain.repository.AuthRepository
import com.mk.datingapp.ui.auth.event.WelScreenEvent
import com.mk.datingapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WelScreenViewModelTest {

    private lateinit var viewModel: WelScreenViewModel

    private val authRepository: AuthRepository = mockk()

    private val analyticsRepository: AnalyticsRepository = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = WelScreenViewModel(
            analyticsRepository,
            authRepository
        )
    }

    //   success path test
    @Test
    fun signInWithGoogle_success_existingUser_navigateToMain() = runTest {
        val fakeUser = AuthUser("uid123", "test@gmail.com", "Manash")

        // coEvery is used for suspend function
        coEvery { authRepository.signInWithGoogle(any<String>()) } returns Result.success(fakeUser)
        coEvery {
            authRepository.checkAndCreateUser(
                any<String>(),
                any<String>(),
                any<String>()
            )
        } returns false

        val events = mutableListOf<WelScreenEvent>()
        // setting an observer whenever sharedflow will emit by the viewmodel functions
        val job = launch {
            viewModel.event.collect { events.add(it) }
        }

        runCurrent() // it helps to run the launch immediately

        // main testing part
        viewModel.signInWithGoogle("token")

        advanceUntilIdle()
        println(events)

        // then
        val state  = viewModel.state.value

        assertFalse(state.isLoading)
        assertNull(state.error)

        assertTrue(events.contains(WelScreenEvent.OnNavigateToMain))

        job.cancel()
    }


}