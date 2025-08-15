package repository

import common.CacheStrategy
import common.OnResult
import database.dao.ContactDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import mappers.toContact
import mappers.toContactEntity
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import preferences.DesafioPreferences
import remote.api.ContactApi
import remote.dto.ContactDto
import remote_config.RemoteConfig
import java.util.concurrent.TimeUnit
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class ContactRepositoryTest {

    // Mocks
    private lateinit var api: ContactApi
    private lateinit var pref: DesafioPreferences
    private lateinit var remoteConfig: RemoteConfig
    private lateinit var dao: ContactDao

    private lateinit var repository: ContactRepositoryImpl


    val contactDto = ContactDto(id = 1, name = "John", username = "johnUserName", img = "img")
    val contact = contactDto.toContact()!!
    val contactEntity = contact.toContactEntity()


    @Before
    fun setup() {
        api = mock()
        pref = mock()
        remoteConfig = mock()
        dao = mock()

        repository = ContactRepositoryImpl(api, pref, remoteConfig, dao)
    }

    @Test
    fun `CacheFirst returns memoryCache when is not empty`() = runTest {
        repository.apply {
            val field = this::class.java.getDeclaredField("memoryCache")
            field.isAccessible = true
            field.set(this, listOf(contact))
        }
        val result = repository.fetchContacts(CacheStrategy.CacheFirst)

        assertTrue(result is OnResult.Success)
        assertEquals(1, (result as OnResult.Success).data.size)
        assertEquals("John", result.data.first().name)
    }

    @Test
    fun `CacheFirst returns DB data when memoryCache is empty`() = runTest {
        whenever(dao.getAll()).thenReturn(listOf(contactEntity))
        val result = repository.fetchContacts(CacheStrategy.CacheFirst)

        assertTrue(result is OnResult.Success)
        assertEquals(1, (result as OnResult.Success).data.size)
    }

    @Test
    fun `CacheFirst calls API when memory and DB are empty`() = runTest {
        whenever(dao.getAll()).thenReturn(emptyList())
        whenever(api.getUsers()).thenReturn(listOf(contactDto))
        whenever(pref.getLong(any(), any())).thenReturn(0L)

        val result = repository.fetchContacts(CacheStrategy.CacheFirst)

        assertTrue(result is OnResult.Success)
        assertEquals(1, (result as OnResult.Success).data.size)
        verify(api).getUsers()
    }

    @Test
    fun `CacheIfIsNotStale returns memoryCache if not expired`() = runTest {
        val field = repository::class.java.getDeclaredField("memoryCache")
        field.isAccessible = true
        field.set(repository, listOf(contact))

        val maxCacheDuration = TimeUnit.HOURS.toMillis(12)
        val lastUpdateTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(11)
        whenever(remoteConfig.getCacheMaxDuration()).thenReturn(maxCacheDuration)
        whenever(pref.getLong(any(), any())).thenReturn(lastUpdateTime)
        val result = repository.fetchContacts(CacheStrategy.CacheIfIsNotStale)

        assertTrue(result is OnResult.Success)
        assertEquals(1, (result as OnResult.Success).data.size)
    }

    @Test
    fun `Strategy CacheIfIsNotStale clears cache when expired and calls API`() = runTest {
        val maxDurationToUseCache = TimeUnit.HOURS.toMillis(12)
        val hoursPassedSinceCacheWasSaved = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(13))

        whenever(remoteConfig.getCacheMaxDuration()).thenReturn(maxDurationToUseCache)
        whenever(pref.getLong(any(), any())).thenReturn(hoursPassedSinceCacheWasSaved)

        whenever(api.getUsers()).thenReturn(listOf(contactDto))

        val result = repository.fetchContacts(CacheStrategy.CacheIfIsNotStale)

        assertTrue(result is OnResult.Success)
        verify(dao).insertAll(any())
        verify(api).getUsers()
    }

    @Test
    fun `FromApi always calls API and saves data`() = runTest {
        whenever(api.getUsers()).thenReturn(listOf(contactDto))
        whenever(pref.getLong(any(), any())).thenReturn(0L)

        val result = repository.fetchContacts(CacheStrategy.FromApi)

        assertTrue(result is OnResult.Success)
        verify(api).getUsers()
        verify(dao).deleteAll()
        verify(dao).insertAll(any())
        verify(pref).putLong(eq(ContactRepositoryImpl.KEY_LAST_UPDATE), any())
    }

    @Test
    fun `fetchContactsFromApi returns error when API fails`() = runTest {
        whenever(api.getUsers()).thenThrow(RuntimeException("API failure"))

        val result = repository.fetchContactsFromApi()

        assertTrue(result is OnResult.Error)
    }

    @Test
    fun `clearContactCache empties memory, DB and prefs`() = runTest {
        repository.clearContactCache()
        verify(dao).deleteAll()
        verify(pref).remove(ContactRepositoryImpl.KEY_LAST_UPDATE)
    }

    @Test
    fun `saveContacts saves to DB and updates last update`() = runTest {
        repository.saveContacts(listOf(contact))
        verify(dao).insertAll(any())
        verify(pref).putLong(eq(ContactRepositoryImpl.KEY_LAST_UPDATE), any())
    }

    @Test
    fun `method isCachedExpired() expired time 1h`() = runTest {
        val lastUpdate2hAgo = (System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))
        val maxDuration1hr = TimeUnit.HOURS.toMillis(1)
        val isExpired = repository
            .isCachedExpired(lastUpdate2hAgo, maxDuration1hr)
        assertTrue(isExpired)
    }

    @Test
    fun `method isCachedExpired() expired time 1m`() = runTest {
        val lastUpdate61MinAgo = (System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(61))
        val maxDuration60Min = TimeUnit.MINUTES.toMillis(60)
        val isExpired = repository
            .isCachedExpired(lastUpdate61MinAgo, maxDuration60Min)
        assertTrue(isExpired)
    }

    @Test
    fun `method isCachedExpired() not expired time 1m`() = runTest {
        val lastUpdate59MinAgo = (System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(59))
        val maxDuration60Min = TimeUnit.MINUTES.toMillis(60)
        val isExpired = repository
            .isCachedExpired(lastUpdate59MinAgo, maxDuration60Min)
        assertFalse(isExpired)
    }

}