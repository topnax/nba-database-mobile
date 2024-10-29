package com.github.topnax.nbadatabasemobile.presentation.screen.list

import com.github.topnax.nbadatabasemobile.data.Page
import com.github.topnax.nbadatabasemobile.data.Player
import com.github.topnax.nbadatabasemobile.data.TeamPreview
import com.github.topnax.nbadatabasemobile.domain.player.PlayerRepository
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewModelScreenContract
import com.github.topnax.nbadatabasemobile.presentation.screen.player.list.PlayersListViewScreenModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
@ExperimentalCoroutinesApi
class PlayersListViewScreenModelTest {

    // Set the main dispatcher to a TestDispatcher
    private val testDispatcher = StandardTestDispatcher()

    // Mocked PlayerRepository
    private lateinit var playerRepository: PlayerRepository

    // ViewModel under test
    private lateinit var viewModel: PlayersListViewScreenModel

    @Before
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this, relaxed = true)

        // Mock the PlayerRepository
        playerRepository = mockk()

        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel with the mocked repository and a small pageSize for testing
        viewModel = PlayersListViewScreenModel(playerRepository, pageSize = 2)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
        // Cleanup the test dispatcher
        testDispatcher.scheduler.cancel()
    }

    @Test
    fun `LoadNextItems event loads players successfully`() = runTest {
        // Given
        val mockTeam = TeamPreview(
            id = "team1",
            name = "Lakers",
            abbreviation = "LAL"
        )

        val mockPlayersPage1 = listOf(
            Player(
                id = "player1",
                firstName = "LeBron",
                lastName = "James",
                position = "SF",
                height = "6'9\"",
                weight = 250,
                jerseyNumber = "23",
                college = "None",
                country = "USA",
                draftYear = 2003,
                draftRound = 1,
                draftNumber = 1,
                teamPreview = mockTeam
            ),
            Player(
                id = "player2",
                firstName = "Anthony",
                lastName = "Davis",
                position = "PF",
                height = "6'10\"",
                weight = 253,
                jerseyNumber = "3",
                college = "Kentucky",
                country = "USA",
                draftYear = 2012,
                draftRound = 1,
                draftNumber = 1,
                teamPreview = mockTeam
            )
        )

        val page1 = Page(
            data = mockPlayersPage1,
            nextCursor = 2 // Next cursor exists
        )

        coEvery { playerRepository.getPlayers(cursor = 0, pageSize = 2) } returns page1

        // When
        viewModel.onEvent(PlayersListViewModelScreenContract.Event.LoadNextItems)

        // Advance coroutine until idle
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertFalse(state.endReached)
        assertEquals(2, state.players!!.size)
        assertEquals(mockPlayersPage1, state.players)

        // Verify that repository was called with correct parameters
        coVerify { playerRepository.getPlayers(cursor = 0, pageSize = 2) }
    }

    @Test
    fun `LoadNextItems event loads multiple pages successfully`() = runTest {
        // Given: First page
        val mockTeam = TeamPreview(
            id = "team1",
            name = "Lakers",
            abbreviation = "LAL"
        )

        val mockPlayersPage1 = listOf(
            Player(
                id = "player1",
                firstName = "LeBron",
                lastName = "James",
                position = "SF",
                height = "6'9\"",
                weight = 250,
                jerseyNumber = "23",
                college = "None",
                country = "USA",
                draftYear = 2003,
                draftRound = 1,
                draftNumber = 1,
                teamPreview = mockTeam
            ),
            Player(
                id = "player2",
                firstName = "Anthony",
                lastName = "Davis",
                position = "PF",
                height = "6'10\"",
                weight = 253,
                jerseyNumber = "3",
                college = "Kentucky",
                country = "USA",
                draftYear = 2012,
                draftRound = 1,
                draftNumber = 1,
                teamPreview = mockTeam
            )
        )

        val page1 = Page(
            data = mockPlayersPage1,
            nextCursor = 2 // Next cursor exists
        )

        coEvery { playerRepository.getPlayers(cursor = 0, pageSize = 2) } returns page1

        // Load first page
        viewModel.onEvent(PlayersListViewModelScreenContract.Event.LoadNextItems)
        testDispatcher.scheduler.advanceUntilIdle()

        // Given: Second page
        val mockPlayersPage2 = listOf(
            Player(
                id = "player3",
                firstName = "Stephen",
                lastName = "Curry",
                position = "PG",
                height = "6'3\"",
                weight = 185,
                jerseyNumber = "30",
                college = "Davidson",
                country = "USA",
                draftYear = 2009,
                draftRound = 1,
                draftNumber = 7,
                teamPreview = mockTeam
            )
        )

        val page2 = Page<Int, List<Player>>(
            data = mockPlayersPage2,
            nextCursor = null // No more pages
        )

        coEvery { playerRepository.getPlayers(cursor = 2, pageSize = 2) } returns page2

        // When: Load second page
        viewModel.onEvent(PlayersListViewModelScreenContract.Event.LoadNextItems)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertTrue(state.endReached)
        assertEquals(3, state.players!!.size)
        assertEquals(mockPlayersPage1 + mockPlayersPage2, state.players)

        // Verify that repository was called with correct parameters
        coVerify { playerRepository.getPlayers(cursor = 0, pageSize = 2) }
        coVerify { playerRepository.getPlayers(cursor = 2, pageSize = 2) }
    }
}
