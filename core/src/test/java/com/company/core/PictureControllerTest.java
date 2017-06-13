package com.company.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@SpringBootTest(classes = CoreApplication.class)
public class PictureControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testFileUpload() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("image", "test.txt", "text/plain", "Spring Framework".getBytes());
        mvc.perform(fileUpload("/image/recognize").file(multipartFile))
                .andExpect(status().isOk());
    }

}
