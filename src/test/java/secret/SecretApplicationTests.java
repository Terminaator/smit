package secret;


import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import secret.controllers.PersonController;
import secret.data.Person;
import secret.data.User;
import secret.data.dto.PersonDTO;

import javax.servlet.ServletContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SecretApplication.class })
@TestPropertySource(locations = "classpath:application.properties")
public class SecretApplicationTests {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void testIfReady() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("personController"));
    }
    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name("index.html"));
    }
    @Test
    public void checkIfAdded() throws Exception {
        Gson gson = new Gson();
        User user = new User();
        user.setUsername("smit");
        user.setPassword("SeeOnSalajane!");
        MvcResult vale = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)))
                .andExpect(status().isOk()).andReturn();

        TokenDTO tokenDTO = gson.fromJson(vale.getResponse().getContentAsString(), TokenDTO.class);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNumber("ee3");
        personDTO.setRealName("ee2");
        personDTO.setSecretName("ee1");
        mockMvc.perform(post("/addPerson").header("Authorization","Bearer " + tokenDTO.getToken())
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(personDTO))).andExpect(status().isOk());
        MvcResult Mresult = mockMvc.perform(get("/getAll").header("Authorization", "Bearer " + tokenDTO.getToken())).andExpect(status().isOk()).andReturn();
        Assert.assertTrue(Mresult.getResponse().getContentAsString().contains("ee3"));
        Assert.assertTrue(Mresult.getResponse().getContentAsString().contains("ee2"));
        Assert.assertTrue(Mresult.getResponse().getContentAsString().contains("ee1"));

    }
}
