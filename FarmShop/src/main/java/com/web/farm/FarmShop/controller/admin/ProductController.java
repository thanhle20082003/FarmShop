package com.web.farm.FarmShop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.farm.FarmShop.domain.Category;
import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.model.CategoryDTO;
import com.web.farm.FarmShop.model.ProductDTO;
import com.web.farm.FarmShop.service.CategoryService;
import com.web.farm.FarmShop.service.ProductService;
import com.web.farm.FarmShop.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    StorageService storageService;

    @ModelAttribute("categories")
    List<CategoryDTO> getCategories() {
        return categoryService.findAll().stream().map(item->{
            CategoryDTO dto = new CategoryDTO();
            BeanUtils.copyProperties(item, dto);

            return dto;
        }).toList();
    }

    @RequestMapping("")
    public String list(
            ModelMap model,
            @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), 5);

        Page<Product> page = productService.findAll(pageable);

        model.addAttribute("products", page);
        return "admin/products/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        ProductDTO dto = new ProductDTO();
        dto.setIsEdit(false);
        model.addAttribute("product", dto);
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") Long id) {
        Optional<Product> optional = productService.findById(id);
        ProductDTO dto = new ProductDTO();
        if(optional.isPresent()) {
            Product entity = optional.get();

            //coppy tu entity sang dto
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);

            model.addAttribute("product", dto);

            return new ModelAndView("admin/products/addOrEdit", model);
        }

        model.addAttribute("message", "Product wasn't existed");

        return new ModelAndView("forward:/admin/products", model);
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> saveFile(@PathVariable String filename){

        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(ModelMap model, @PathVariable ("id") Long id) {

        productService.deleteById(id);

        model.addAttribute("message", "Delete was successed");

        return new ModelAndView("forward:/admin/products", model);
    }

//    @PostMapping("saveOrUpdate")
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDTO dto, BindingResult result) {

        if(result.hasErrors()) {
            //nap lai trang addOrEdit
            return new ModelAndView("admin/products/addOrEdit");
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        Category category = new Category();
        category.setId(dto.getCategoryId());
        entity.setCategory(category);

        if(!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();

            entity.setImage(storageService.getStoredFileName(dto.getImageFile(), uuString));
            storageService.store(dto.getImageFile(), entity.getImage());
        }

        productService.save(entity);

        model.addAttribute("message", "Product was saved");

        return new ModelAndView("forward:/admin/products", model);
    }

    @GetMapping("search")
    public String search(
            ModelMap model,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), 5);
        Page<Product> list = null;
        if (StringUtils.hasText(name)) {
            list = productService.findAllByNameLike("%"+name+"%",pageable);
        }else {
            list = productService.findAll(pageable);
        }
        model.addAttribute("name", name);
        model.addAttribute("products", list);
        return "admin/products/list";
    }

}
