package fraudulent;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
public class LaunchApplication {

	public static void main(String[] args) {
		System.out.println("=== MAIN DEL LAUNCHER ===");
		SpringApplication.run(LaunchApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		System.out.println("Petición recibida en /");
		return "Launcher funcionando";
	}

	@PostConstruct
	public void arrancarJar() {

		try {
			System.out.println("=== 1. INICIO LAUNCHER ===");

			System.out.println("=== 2. DIRECTORIO ACTUAL ===");
			System.out.println(System.getProperty("user.dir"));

			File jar = new File("app/shop-1.0.0.jar");

			System.out.println("=== 3. COMPROBANDO JAR ===");
			System.out.println("Existe: " + jar.exists());
			System.out.println("Ruta: " + jar.getAbsolutePath());
			System.out.println("Tamaño: " + jar.length());

			if (!jar.exists()) {
				System.out.println("=== ERROR: EL JAR NO EXISTE ===");
				return;
			}

			System.out.println("=== 4. LANZANDO JAR ===");

			ProcessBuilder pb = new ProcessBuilder("java", "-jar", jar.getAbsolutePath());

			pb.redirectErrorStream(true);

			Process process = pb.start();

			System.out.println("=== 5. PROCESO CREADO ===");
			System.out.println("PID: " + process.pid());

			new Thread(() -> {
				try {
					process.getInputStream().transferTo(System.out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			System.out.println("=== 6. JAR LANZADO ===");

		} catch (Exception e) {
			System.out.println("=== ERROR LANZANDO JAR ===");
			e.printStackTrace();
		}
	}
}