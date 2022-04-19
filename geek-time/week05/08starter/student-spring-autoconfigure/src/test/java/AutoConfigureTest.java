import com.kong.starter.model.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/25 21:36
 * @Description:
 */
@SpringBootTest
public class AutoConfigureTest {

	@Autowired
	private Student student;

	@Test
	public void autoTest(){

		System.out.println(student);
	}
}
