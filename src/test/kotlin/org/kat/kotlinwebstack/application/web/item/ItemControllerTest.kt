package org.kat.kotlinwebstack.application.web.item

import io.javalin.Context
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class ItemControllerTest {

    @MockK
    lateinit var context: Context

    @MockK
    var controller = ItemController()

    @Test
    fun getItemKTorAPI() {
        val item = controller.getItem(0)
        assertNotNull(item)
        assertNotNull(item.name)
        assertNotNull(item.quantity)
    }

    @Test
    fun getItemJavalinAPI() {
        every { controller.getItem(context) } just Runs

        controller.getItem(context)

        verify { controller.getItem(context) }
    }
}