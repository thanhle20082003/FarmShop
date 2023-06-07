package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.service.ProductService;
import com.web.farm.FarmShop.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("site")
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private StorageService storageService;

	@GetMapping("")
	public String index() {

		return "site/fragments/home";
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){

		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);

	}

	@GetMapping("product")
	public String product(ModelMap model,
						  @RequestParam(defaultValue = "0") int pageNumber,
						  @RequestParam(defaultValue = "9") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Product> page = productService.findAll(pageable);

		model.addAttribute("products", page.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", page.getTotalPages());

		return "site/fragments/product";
	}
	
	@GetMapping("contact")
	public String contactUs() {
		return "site/fragments/contact";
	}
	
	@GetMapping("about")
	public String aboutUs() {
		return "site/fragments/about";
	}
	
	@GetMapping("login")
	public String login() {
		return "site/customer/login";
	}
	
	@GetMapping("register")
	public String register() {
		return "site/customer/register";
	}
	
}
