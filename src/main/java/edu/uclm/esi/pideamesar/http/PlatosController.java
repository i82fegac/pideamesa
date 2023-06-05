package edu.uclm.esi.pideamesar.http;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.pideamesar.model.Bar;
import edu.uclm.esi.pideamesar.model.Comanda;
import edu.uclm.esi.pideamesar.model.Plato;
import edu.uclm.esi.pideamesar.services.BarService;
import edu.uclm.esi.pideamesar.services.ComandaService;
import edu.uclm.esi.pideamesar.services.PlatoService;

@RestController
@RequestMapping("platos")
public class PlatosController extends CookiesController {
		
	@Autowired
	private ComandaService comandaService;

	@Autowired
	private BarService barService;
	
	@Autowired
	private PlatoService platoService;
	
	@PostMapping("/add")
	public void add(HttpSession session, @RequestBody Map<String, Object> info) {
		if (session.getAttribute("idBar")==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Por favor, elige un establecimiento para continuar");
		
		Comanda comanda = (Comanda) session.getAttribute("comanda");
		if (comanda==null) {
			comanda = new Comanda();
			session.setAttribute("comanda", comanda);
		}
		
		JSONObject jso = new JSONObject(info);
		String idPlato = jso.getString("idPlato");
		comandaService.add(comanda, idPlato, jso.getDouble("cantidad"));		
	}
		
	@GetMapping("/load/{barId}")
	public List<Plato> load(HttpSession session, @PathVariable String barId) {
		session.setAttribute("idBar", barId);
		return this.platoService.getPlatosDeBar(barId);	
	}
	
	@PostMapping("/getBares")
	public List<Bar> getBares(HttpSession session, @RequestBody Map<String, Object> info) {	
		return this.barService.getBares();
	}

}
