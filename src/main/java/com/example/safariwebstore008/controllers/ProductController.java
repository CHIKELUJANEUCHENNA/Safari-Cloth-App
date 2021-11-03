package com.example.safariwebstore008.controllers;
import com.example.safariwebstore008.models.Product;
import com.example.safariwebstore008.models.ProductImages;
import com.example.safariwebstore008.services.ProductService;
import com.example.safariwebstore008.services.servicesImpl.CartServiceImpl;
import com.example.safariwebstore008.services.servicesImpl.FavouritesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private FavouritesServiceImpl favouritesService;


    @GetMapping("/")
    public ResponseEntity<List<Product>> viewAllProducts(@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                                         @RequestParam (value = "pageSize", required = false, defaultValue = "4") int pageSize){
        List<Product> response = productService.adminViewAllProductsPaginated(pageNo,pageSize);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> viewASingleProduct(@PathVariable(value = "productId") Long productId){
        Product product = productService.adminFetchParticularProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<?> searchProductByKeyword(@RequestParam String keyword) {
        List<Product> productList = productService.searchProductsByKeyword(keyword);
        if (productList.size() == 0) {
            return ResponseEntity.ok("No matching product found");
        }
        return ResponseEntity.ok(productList);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/add_to_cart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable Long id, HttpServletRequest req){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        cartService.addToCart(id,email);
        return new ResponseEntity<>("Added successfully",HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping ("/add_favorite/{id}")
    public ResponseEntity<Object> addToFavourites(@PathVariable Long id, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userEmail = principal.getName();
        boolean message =  favouritesService.findFavouritesByProductsAndUserModel(userEmail,id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
