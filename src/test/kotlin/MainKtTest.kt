
import org.example.gift_card.GiftCardApp
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = [GiftCardApp::class])
class AmexGiftCardTest {


    @Test
    fun contextLoads() {
        // This test simply checks if the Spring context loads without errors
        // You can add more specific test cases based on your requirements
    }

}