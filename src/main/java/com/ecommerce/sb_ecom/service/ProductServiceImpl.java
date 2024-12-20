package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.Repository.CategoryRepository;
import com.ecommerce.sb_ecom.Repository.ProductRepository;
import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        boolean isProductNotPresent= true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() -
                    ((product.getDiscount()*0.01) *product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else {
           throw new APIException("Product already exists");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products=productPage.getContent();
        List<ProductDTO> productDTOS= products.stream().map(product ->
                modelMapper.map(product,ProductDTO.class)).toList();
        if(products.isEmpty()){
            throw new APIException("No products found");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchByCatagory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category","categoryId",categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);

        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS= products.stream().map(
                product ->modelMapper.map(product,ProductDTO.class)).toList();

        if(products.size()==0){
            throw new APIException("This category does not have any products");
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%',pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product ->
                modelMapper.map(product,ProductDTO.class)).toList();
        if(products.size()==0){
            throw new APIException("No products found with keyword: "+ keyword);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOS);

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productFound = productRepository.findById(productId).orElseThrow(() ->
                        new ResourceNotFoundException("Product","productId",productId));
         Product product = modelMapper.map(productDTO, Product.class);
         productFound.setProductName(product.getProductName());
         productFound.setDescription(product.getDescription());
         productFound.setPrice(product.getPrice());
         productFound.setDiscount(product.getDiscount());
         productFound.setQuantity(product.getQuantity());
        double specialPrice = product.getPrice() -
                ((product.getDiscount()*0.01) *product.getPrice());
        productFound.setSpecialPrice(specialPrice);

        Product savedProduct=productRepository.save(productFound);

        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product searchedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));
        productRepository.delete(searchedProduct);
        return modelMapper.map(searchedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        //Get the product from DB
        Product productFromDB = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));
        //upload image to server
        //Get the file name of th uploaded image

        String fileName = fileService.uploadImage(path,image);
        // Updating th new file image to the product
        productFromDB.setImage(fileName);
        //save product
        Product savedProduct=productRepository.save(productFromDB);
        //return DTO after mapping
        return modelMapper.map(savedProduct,ProductDTO.class);
    }




}
