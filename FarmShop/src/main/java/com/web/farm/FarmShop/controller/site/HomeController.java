package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Category;
import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.service.CategoryService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("site")
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String index(ModelMap model,
						@RequestParam(defaultValue = "0") int page,
						@RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
		List<Product> list = productService.findProductByStatus(pageable);
		model.addAttribute("products", list);
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
						  @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber,
						  @RequestParam(defaultValue = "9") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize);

		Page<Product> page = productService.findAll(pageable);

		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("products", page.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", page.getTotalPages());

		return "site/fragments/product";
	}

	@GetMapping("product/{categoryId}")
	public String findProductByCategoryID(ModelMap model,
										  @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber,
										  @RequestParam(defaultValue = "9") int pageSize,
										  @PathVariable ("categoryId") Long id) {
		Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize);

		Page<Product> page = productService.findProductByCategoryId(id, pageable);

		model.addAttribute("categories", categoryService.findAll());
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
