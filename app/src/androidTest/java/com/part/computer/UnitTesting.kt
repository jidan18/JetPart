
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import com.part.computer.R
import com.part.computer.ui.components.CartItem
import com.part.computer.ui.theme.JetPartTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchTextField() {
        var searchText by mutableStateOf("")

        composeTestRule.setContent {
            SearchTextField(
                value = searchText,
                onValueChange = { searchText = it },
                onSearch = {}
            )
        }

        // Find the text field by content description
        val searchTextField = composeTestRule.onNodeWithContentDescription("Search")

        // Assert the initial state
        searchTextField.assertHasClickAction()

        // Perform actions in the Compose UI
        composeTestRule.onNodeWithContentDescription("Search").performTextInput("Test")

        // Assert the expected results
        searchTextField.assertTextEquals("Test")
    }
}

@Composable
fun SearchTextField(value: String, onValueChange: (String) -> Unit, onSearch: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text(stringResource(id = R.string.search_hint)) },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(16.dp)
            .heightIn(min = 48.dp)
            .testTag("searchTextField")  // Set a test tag for easy identification in tests
    )
}


class CartItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCartItem() {
        val rewardId = 4L
        val image = R.drawable.reward_4
        val title = "Jaket Hoodie Dicoding"
        val totalPoint = 4000
        val count = 0

        // Set up the composable with the test data
        composeTestRule.setContent {
            JetPartTheme {
                CartItem(
                    rewardId = rewardId,
                    image = image,
                    title = title,
                    totalPoint = totalPoint,
                    count = count,
                    onProductCountChanged = { _, _ -> },
                    modifier = Modifier.testTag("cartItem")
                )
            }
        }

        // Assert the presence of UI components based on their content descriptions, tags, etc.
        composeTestRule.onNodeWithContentDescription(null.toString())
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("cartItem")
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Jaket Hoodie Dicoding")
            .assertIsDisplayed()
            .assertTextContains(title)

        composeTestRule.onNodeWithText("4000")
            .assertIsDisplayed()
            .assertTextContains(totalPoint.toString())

        composeTestRule.onNodeWithTag("partCounter")
            .assertIsDisplayed()
    }
}