package com.ecommerce.controller;


import java.io.IOException;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UploadfileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER=LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private UploadfileService upload;
	
	/* Llamado a la tabla con datos productos, formulario y metodo de crear productos */
	 
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException{
		LOGGER.info("Este es el objeto producto {}", producto);
		Usuario u=new Usuario(1,"","","","","","","");
		producto.setUsuario(u);
		
		if(producto.getId()==null) {//cuando se crea un producto
			String nombreImage=upload.saveImage(file);
			producto.setImagen(nombreImage);
		}		
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	/* El llamado a la vista editar con sus respetivos datos y metodo para actualizar Producto  */
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto =new Producto();
		Optional<Producto> optionalProducto=productoService.get(id);
		producto=optionalProducto.get();
		LOGGER.info("PRODUCTO BUSCADO: {}", producto);
		model.addAttribute("producto", producto);
		return "productos/edit";
	}
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
	
		Producto p=new Producto();
		p=productoService.get(producto.getId()).get();
		
		if(file.isEmpty()) {

			producto.setImagen(p.getImagen());
			
		}else {//cuado se edita tabie la imagen
			
	
			//elimina cuando no sea la image por defecto
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}
			
			String nombreImage=upload.saveImage(file);
			producto.setImagen(nombreImage);
		}
	producto.setUsuario(p.getUsuario());
	productoService.update(producto);
	return "redirect:/productos";
	}
	
	
	/* metodo para eliminar un producto */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p=new Producto();
		p=productoService.get(id).get();
		//elimina cuando no sea la image por defecto
		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}
		
		productoService.delete(id);
		return "redirect:/productos";
	}
	
	/**/
	
}
