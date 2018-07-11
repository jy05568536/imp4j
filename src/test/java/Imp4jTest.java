import com.fermii.imp4j.Application;
import com.fermii.imp4j.service.Imp4jService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Imp4jTest {

    @Autowired
    private Imp4jService imp4jService;

    @Test
    public void impData() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.xlsx", "test.xlsx", null, new FileInputStream(new File("D:/test.xlsx")));
        int row = imp4jService.impData("gas", (MultipartFile) mockMultipartFile);
        System.out.println(row);
    }
}

