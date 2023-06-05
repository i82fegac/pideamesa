package edu.uclm.esi.pideamesar;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import edu.uclm.esi.pideamesar.http.Manager;
import edu.uclm.esi.pideamesar.model.Bar;
import edu.uclm.esi.pideamesar.model.Plato;

@SpringBootApplication
@ServletComponentScan
public class LanzadoraPM extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LanzadoraPM.class, args);
		
		Manager.get().getPlatoRepository().deleteAll();
		Manager.get().getBarRepository().deleteAll();
		createBares();
		
		Bar bar = Manager.get().getBarRepository().findByNombre("Restaurante Agar Agar- food & design");
		guarda(bar, "agaragar.txt");
		
		bar = Manager.get().getBarRepository().findByNombre("Asador Restaurante San Huberto");
		guarda(bar, "san_huberto.txt");
		
		bar = Manager.get().getBarRepository().findByNombre("Octavio");
		guarda(bar, "octavio.txt");
	}
	
	private static void guarda(Bar bar, String fichero) {
		try {
			String bares = Manager.get().readTextFile(fichero);
			String[] datos = bares.split("\n");
			for (int i=0; i<datos.length; i++) {
				String[] dato = datos[i].split("\t");
				Plato plato = new Plato();
				plato.setBar(bar);
				plato.setNombre(dato[0]);
				plato.setPvp(Float.parseFloat(dato[1]));
				
				Manager.get().getPlatoRepository().save(plato);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void createBares() {
		try {
			String bares = Manager.get().readTextFile("bares.txt");
			String[] datos = bares.split("\n");
			for (int i=0; i<datos.length; i++) {
				String[] dato = datos[i].split("\t");
				String nombre = dato[0];
				double latitud = Double.parseDouble(dato[1]);
				double longitud = Double.parseDouble(dato[2]);
				Bar bar = new Bar();
				bar.setNombre(nombre);
				bar.setLatitud(latitud);
				bar.setLongitud(longitud);
				
				Manager.get().getBarRepository().save(bar);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LanzadoraPM.class);
    }
}
