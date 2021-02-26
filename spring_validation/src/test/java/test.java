import com.kk.validation.ValidationApplication;
import com.kk.validation.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author luokexiong
 * @version 1.0 2021/2/26
 * @since 1.9.0
 */
@SpringBootTest
@ContextConfiguration(classes = ValidationApplication.class)
public class test {
    @Autowired
    private TestConfig testConfig;

    @Test
    void testYaml() {
        System.out.println(testConfig.getMyConfig());
    }
}
