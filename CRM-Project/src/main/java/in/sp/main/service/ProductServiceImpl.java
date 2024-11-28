package in.sp.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entity.Product;
import in.sp.main.entity.SaleCourse;
import in.sp.main.repository.ProductRepository;
import in.sp.main.repository.SaleCourseRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SaleCourseRepository saleCourseRepository;
	
	@Override
	public boolean addProductService(Product product) {
		boolean status=false;
		
		try 
		{
			productRepository.save(product);
			status=true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			status=false;
		}
		return status;
	}

	@Override
	public List<Product> getAllProductService() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductDetailsService(String coursename) {
		return productRepository.findByCoursename(coursename);
	}

	@Override
	public List<String> getAllCourseNameService() {
		// TODO Auto-generated method stub
		return productRepository.findCourseName();
	}

	@Override
	public Product getSelectedCourseDetailsService(String course) {

		return productRepository.findByCoursename(course);
	}

	@Override
	public boolean addSaleCourseDetailsService(SaleCourse saleCourse) {
		boolean status=false;
		try
		{
			saleCourseRepository.save(saleCourse);
			status=true;
		}
		catch(Exception e)
		{
			status=false;
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<Object[]> getPurchasedCourseCountService() {
		// TODO Auto-generated method stub
		return saleCourseRepository.countByPurchasedDate();
	}

}
