package com.example.myapplication.transformer

import com.example.myapplication.data.model.Item
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ItemTransformerUnitTest {
    @Test
    fun test_create_items() {
        val output = ItemListTransformer().createItems(mockItems())
        assertEquals(mockOutput(), output)
    }

    @Test
    fun test_extract_number() {
        var output = ItemListTransformer().extractNumber("Item12345")
        assertEquals(12345, output)
        output = ItemListTransformer().extractNumber("Item     12345")
        assertEquals(12345, output)
        // stops at space, so 45 is expected
        output = ItemListTransformer().extractNumber("Item     123 45")
        assertEquals(45, output)
        // fails since space comes first
        output = ItemListTransformer().extractNumber("Item     12345      ")
        assertEquals(Int.MAX_VALUE, output)
    }

    private fun mockItems(): List<Item> {
        return listOf(
            Item(id = 10, listId = 4, name = "Item 10"),
            Item(id = 9, listId = 3, name = "Item 9"),
            Item(id = 8, listId = 2, name = "Item 8"),
            Item(id = 7, listId = 2, name = "Item 7"),
            Item(id = 6, listId = 1, name = "Item 6"),
            Item(id = 5, listId = 3, name = "Item 5"),
            Item(id = 4, listId = 4, name = "Item 4"),
            Item(id = 3, listId = 2, name = "Item 3"),
            Item(id = 2, listId = 1, name = "Item 2"),
            Item(id = 1, listId = 3, name = "Item 1"),
            Item(id = 30, listId = 1, name = "Item 30"),
            Item(id = 20, listId = 4, name = "Item 20")
        )
    }

    private fun mockOutput(): LinkedHashMap<Int, List<Item>> {
        return LinkedHashMap<Int, List<Item>>().apply {
            put(
                1, listOf(
                    Item(id = 2, listId = 1, name = "Item 2"),
                    Item(id = 6, listId = 1, name = "Item 6"),
                    Item(id = 30, listId = 1, name = "Item 30")
                )
            )
            put(
                2, listOf(
                    Item(id = 3, listId = 2, name = "Item 3"),
                    Item(id = 7, listId = 2, name = "Item 7"),
                    Item(id = 8, listId = 2, name = "Item 8")
                )
            )
            put(
                3, listOf(
                    Item(id = 1, listId = 3, name = "Item 1"),
                    Item(id = 5, listId = 3, name = "Item 5"),
                    Item(id = 9, listId = 3, name = "Item 9")
                )
            )
            put(
                4, listOf(
                    Item(id = 4, listId = 4, name = "Item 4"),
                    Item(id = 10, listId = 4, name = "Item 10"),
                    Item(id = 20, listId = 4, name = "Item 20")
                )
            )
        }
    }
}