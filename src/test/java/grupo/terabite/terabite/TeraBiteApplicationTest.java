package grupo.terabite.terabite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Aplicação")
class TeraBiteApplicationTest {

    @Test
    @DisplayName("Build")
    public void build() {
        boolean builded = false;

        try {
            TeraBiteApplication.main(new String[]{});

            builded = true;
        } catch (Exception e) {
            fail("Erro ao iniciar aplicação: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertTrue(builded);
    }
}