import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PruebaBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String nuevaContrasena = "12345"; // Contraseña original

        // Generar el hash
        String nuevoHash = passwordEncoder.encode(nuevaContrasena);
        System.out.println("Nuevo hash generado: " + nuevoHash);
    }
}
