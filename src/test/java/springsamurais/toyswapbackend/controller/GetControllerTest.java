package springsamurais.toyswapbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springsamurais.toyswapbackend.model.User;
import springsamurais.toyswapbackend.service.GetService;
import springsamurais.toyswapbackend.service.GetServiceImplementation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class GetControllerTest {

    @Mock
    private GetServiceImplementation mockGetServiceImplementation;

    @InjectMocks
    private GetController getController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}



