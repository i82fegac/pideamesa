package edu.uclm.esi.pideamesar.http;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.pideamesar.model.User;
import edu.uclm.esi.pideamesar.services.UserService;

@RestController
@RequestMapping("user")
public class UserController extends CookiesController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> info) {
		JSONObject jso = new JSONObject(info);
		String name = jso.getString("name");
		String pwd = jso.getString("pwd");
		String ip = request.getRemoteAddr();
		User user = Manager.get().getUsersRepository().findByNameAndPwd(name, pwd);
		if (user==null) //  || user.getConfirmationDate()==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales no válidas o cuenta no validada");
		
		Cookie cookie = readOrCreateCookie(request, response);
		userService.insertLogin(user, ip, cookie);
		request.getSession().setAttribute("userId", user.getId());
	}

	@PutMapping("/register")
	@ResponseBody
	public String register(@RequestBody Map<String, Object> info) {
		JSONObject jso = new JSONObject(info);
		String userName = jso.optString("userName");
		String email = jso.optString("email");
		String pwd1 = jso.optString("pwd1");
		String pwd2 = jso.optString("pwd2");
		String picture = jso.optString("picture");		
		if (!pwd1.equals(pwd2))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: las contraseñas no coinciden");
		if (pwd1.length()<4)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: la contraseña debe tener al menos cuatro caracteres");
		
		User user = new User();
		user.setName(userName);
		user.setEmail(email);
		user.setPwd(pwd1);
		user.setPicture(picture);
		
		try {
			userService.save(user);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Te hemos enviado un correo para confirmar tu registro";
	}
	
	@DeleteMapping("/remove/{userId}")
	public void remove(@PathVariable String userId) {
		System.out.println("Borrar el usuario con id " + userId);		
	}
	
	@GetMapping("/validateAccount/{tokenId}")
	public void validateAccount(HttpServletResponse response, @PathVariable String tokenId) {
		userService.validateToken(tokenId);
		// Ir a la base de datos, buscar el token con ese tokenId en la tabla, ver que no ha caducado
		// y actualizar la confirmationDate del user
		System.out.println(tokenId);
		try {
			response.sendRedirect(Manager.get().getConfiguration().getString("home"));
		} catch (IOException e) {
			
		}
	}
	
	@GetMapping("/recoverPwd")
	public String recoverPwd(@RequestParam String email) {
		try {
			userService.recoverPwd(email);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Te hemos enviado tu contraseña al correo con el que estás registrado en pideamesa.com";
		
	}
	
	@GetMapping("/recoverPwd2/{tokenId}")
	public void recoverPwd2(HttpServletResponse response, @PathVariable String tokenId) {
		// Ir a la base de datos, buscar el token con ese tokenId en la tabla, ver que no ha caducado
		// y pasar a enseñar la contraseña
		String url=userService.showPwd(tokenId);
		//System.out.println(tokenId);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			//response.sendRedirect("error.html");
			e.printStackTrace();
		}
	}
	
	@PutMapping("/updatePwd")
	@ResponseBody
	public String updatePwd(@RequestBody Map<String, Object> info) {
		JSONObject jso = new JSONObject(info);
		String pwd1 = jso.optString("pwd1");
		String pwd2 = jso.optString("pwd2");
		String token =jso.optString("tokenId");
		if (!pwd1.equals(pwd2))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: las contraseñas no coinciden");
		if (pwd1.length()<4)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: la contraseña debe tener al menos cuatro caracteres");		
		try {
			userService.updatePwd(pwd1, token);
		} catch (ResponseStatusException e) {
			throw e;
		}

		return "Contraseña actualizada con éxito";
	}

}
