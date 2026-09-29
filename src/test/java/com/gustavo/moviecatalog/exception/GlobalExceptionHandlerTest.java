package com.gustavo.moviecatalog.exception;

import com.gustavo.moviecatalog.controller.MovieController;
import com.gustavo.moviecatalog.service.MovieService;
import com.gustavo.moviecatalog.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ReviewService reviewService;

    @Test
    void jsonMalFormadoDevolve400() throws Exception {
        mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON).content("{\"title\": "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Corpo da requisição inválido"));
    }

    @Test
    void generoInexistenteNoCorpoDevolve400() throws Exception {
        String filme = "{\"title\":\"Filme\",\"director\":\"Alguém\",\"genre\":\"NAO_EXISTE\"}";

        mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON).content(filme))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Corpo da requisição inválido"));
    }

    @Test
    void idComLetraDevolve400() throws Exception {
        mockMvc.perform(get("/api/movies/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Parâmetro inválido"))
                .andExpect(jsonPath("$.message").value(containsString("'id'")));
    }

    @Test
    void generoInexistenteNoEnderecoDevolve400() throws Exception {
        mockMvc.perform(get("/api/movies/genre/NAO_EXISTE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("'genre'")));
    }

    @Test
    void parametroObrigatorioAusenteDevolve400() throws Exception {
        mockMvc.perform(get("/api/movies/search"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Parâmetro obrigatório ausente"))
                .andExpect(jsonPath("$.message").value(containsString("'keyword'")));
    }

    @Test
    void metodoNaoSuportadoDevolve405() throws Exception {
        mockMvc.perform(patch("/api/movies"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.error").value("Método não permitido"));
    }

    @Test
    void corpoQueNaoEJsonDevolve415() throws Exception {
        mockMvc.perform(post("/api/movies").contentType(MediaType.TEXT_PLAIN).content("oi"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.error").value("Tipo de conteúdo não suportado"));
    }

    @Test
    void enderecoInexistenteDevolve404() throws Exception {
        mockMvc.perform(get("/nao-existe"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Endereço não encontrado"));
    }

    @Test
    void filmeInexistenteDevolve404() throws Exception {
        when(movieService.findById(999L)).thenThrow(new ResourceNotFoundException("Filme não encontrado com id: 999"));

        mockMvc.perform(get("/api/movies/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Filme não encontrado com id: 999"));
    }

    @Test
    void camposInvalidosDevolvem400ComCadaCampo() throws Exception {
        mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.fields.title").exists())
                .andExpect(jsonPath("$.fields.genre").exists());
    }

    @Test
    void erroInesperadoDevolve500SemExporDetalheInterno() throws Exception {
        when(movieService.findAll()).thenThrow(new IllegalStateException("senha do banco: s3gredo"));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Erro interno do servidor"))
                .andExpect(jsonPath("$.message").value(not(containsString("s3gredo"))));
    }
}
