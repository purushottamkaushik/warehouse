package com.warehouse.restControllers;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.properties")
public class TestUomRestController {

    @Autowired
    private MockMvc mockMvc; // To create mockObject to perform request


    @DisplayName("Test Save Uom ")
    @Order(1)
    @Test
    @Disabled
    public void testSaveUom() throws Exception {
        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.post("/rest/api/v1/uom/create").content("{\"description\": \"AAAA\",\"id\": null,\"uomModel\": \"M111\",\"uomType\": \"HELLO\"}"
        ).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());

        if (!response.getContentAsString().contains("created")) {
            fail("Uom May not be created ");
        }    }

    @DisplayName("Test Delete Uom Success ")
    @Order(2)
    @Test
    @Disabled
    public void testUomDeleteSuccess() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.delete("/rest/api/v1/uom/delete/{id}",52).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        if (!response.getContentAsString().contains("deleted")) {
            fail("Uom May not be deleted ");
        }
    }


    @DisplayName("Test Uom Delete Fail")
    @Order(3)
    @Test
    @Disabled
    public void testUomDeleteFail() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.delete("/rest/api/v1/uom/delete/{id}",3).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response

        assertEquals("Status not Matched ",HttpStatus.INTERNAL_SERVER_ERROR.value(),response.getStatus());
        if (!response.getContentAsString().contains("not found")) {
            fail("Uom may not be deleted ");
        }
    }

    @DisplayName("Test Uom Get All ")
    @Order(4)
    @Test
    @Disabled
    public void testUomGetAll() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.get("/rest/api/v1/uom/all").contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals("Status not matched",HttpStatus.OK.value(),response.getStatus());
        assertEquals("Content Type Not matched ",MediaType.APPLICATION_JSON_VALUE,response.getContentType());
        if (response.getContentAsString().length()<=2) {
            fail("No Data for Uom Available");
        }

    }

    @DisplayName("Test Uom Get One Suceess")
    @Order(5)
    @Test
    @Disabled
    public void getUomGetOneSuccess() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.get("/rest/api/v1/uom/find/{id}",1).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals("Status not matched " , HttpStatus.OK.value(),response.getStatus());
        assertEquals("Content Type Not matched " , MediaType.APPLICATION_JSON_VALUE,response.getContentType());
        if (response.getContentAsString().length()<=2) {
            fail("No Uom Exist for Fetching");
        }

    }

    @DisplayName("Test Uom get One Fail ")
    @Order(6)
    @Test
    @Disabled
    public void getUomGetOneFail() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.get("/rest/api/v1/uom/find/{id}",1).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals("Status not matched " , HttpStatus.INTERNAL_SERVER_ERROR.value(),response.getStatus());
        if (!response.getContentAsString().contains("not found") ) {
            fail("May Be Uom exists ");
        }
    }

    @DisplayName("Test UOM update ")
    @Order(7)
    @Test
    public void getUomUpdate() throws Exception {

        // 1 . Create Mock request
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.put("/rest/api/v1/uom/update/").content("{\"description\": \"AAA\",\"id\": 112,\"uomModel\": \"M111\",\"uomType\": \"HELLO\"}"
        ).contentType(MediaType.APPLICATION_JSON);

        // 2 . Execute the request and get result
        MvcResult result =  mockMvc.perform(request).andReturn();
        // 3 . Extract Response
        MockHttpServletResponse response =  result.getResponse();
        //4. Verify and test the response
        assertEquals("Status not Matched" ,HttpStatus.OK.value(),response.getStatus());
        if (!response.getContentAsString().contains("updated  Successfully")) {
            fail("Fail to update Uom");
        }
    }



}
