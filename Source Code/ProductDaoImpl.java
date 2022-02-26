/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of every team member to the Provost Office for academic 
 * dishonesty. 
 */ 

package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{

	private static final String insertSQL = 
			"INSERT INTO PRODUCT (prod_name, prod_desc, prod_category,prod_upc) VALUES (?, ?, ?, ?);";
	
    @Override
    public Product create(Connection connection, Product product) throws SQLException, DAOException
    {

    		if (product.getId() != null) {
    			throw new DAOException("inserting a product with a non-null id");
    		}
    	
    		
    		PreparedStatement ps = null;
    		try 
    		{
    			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
    			ps.setString(1, product.getProdName());
    			ps.setString(2, product.getProdDescription());
    			ps.setInt(3, product.getProdCategory());
    			ps.setString(4, product.getProdUPC());
    			ps.executeUpdate();
    			
    			
    			ResultSet keyRS = ps.getGeneratedKeys();
    			keyRS.next();
    			int lastKey = keyRS.getInt(1);
    			product.setId((long) lastKey);
    			
    			return product;
    		}
    		
    		finally 
    		{
    			if (ps != null && !ps.isClosed())
    			{
    				ps.close();
    			}
    		}
    	
    	
    }
    
   private static final String selectSQL =
			"SELECT id, prod_name, prod_desc, prod_category, prod_upc FROM product WHERE id = ?;";

    @Override
    public Product retrieve(Connection connection, Long id) throws SQLException, DAOException
    {
    	
    	
		if (id == null){
				throw new DAOException("Retrieving product with null value for id");
		}
		
		PreparedStatement ps = null;
		
		try{
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, id);  
			ResultSet resultSet = ps.executeQuery();
			
			if (!resultSet.next()) {
				return null;
			}
			
			Product product = new Product();
		
			
			product.setId(resultSet.getLong("id"));
			product.setProdName(resultSet.getString("prod_name"));
			product.setProdDescription(resultSet.getString("prod_desc"));
			product.setProdCategory(resultSet.getInt("prod_category"));
			product.setProdUPC(resultSet.getString("prod_upc"));
			return product;
		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) 
			{
				ps.close();
			}
		}
        
    }
    
    private static final String updateSQL = "UPDATE product SET prod_name = ?, prod_desc = ?, prod_category = ?, prod_upc = ? WHERE id = ?;"; 

    @Override
    public int update(Connection connection, Product product) throws SQLException, DAOException
    {
        
		if (product.getId() == null)
		{
			throw new DAOException("Updating product with null value for ID");
		}
		
		PreparedStatement ps = null; 
		
		try
		{
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());
			int rowset = ps.executeUpdate();
			
			return rowset;
		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) 
			{
				ps.close();
			}
		}
			
		
    }

	private static final String deleteSQL = "DELETE FROM product WHERE id = ?;"; 
	
    @Override
    public int delete(Connection connection, Long id) throws SQLException, DAOException
    {
		
    	if (id == null)
    	{
			throw new DAOException("Deleting from product with a null value for ID");
		}
    	
		PreparedStatement ps = null;
		
		try
		{
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);
			
			int rowset = ps.executeUpdate();
			return rowset;
		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
       
    }
    
    private static final String retrieveSQL = "SELECT id, prod_name, prod_desc, prod_category, prod_upc FROM product WHERE prod_category = ?;";

    @Override
    public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException
    {
    	
    	if (category < 0)
    	{
    		throw new DAOException("Category value is not valid.");
    	}
    	
    	PreparedStatement ps = null;
    	
    	try{
    		
    		ps = connection.prepareStatement(retrieveSQL);
			ps.setInt(1, category);  
			ResultSet resultSet = ps.executeQuery();
			
			if (!resultSet.next())
			{
				return null;
			}
			
			//make list of product objects 
			List<Product> listProd = new ArrayList<Product>(); 
			
			//iterate through result set 
			while (resultSet.next())
			{
				Product product = new Product();
				product.setId(resultSet.getLong("id"));
				product.setProdName(resultSet.getString("prod_name"));
				product.setProdDescription(resultSet.getString("prod_desc"));
				product.setProdCategory(resultSet.getInt("prod_category"));
				product.setProdUPC(resultSet.getString("prod_upc"));
				listProd.add(product); 
			}

		
			return listProd;
		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
    	
    	
    }
    
   private  final String upcSQL = "SELECT id, prod_name, prod_desc, prod_category, prod_upc FROM product WHERE prod_upc = ?";

    @Override
    public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException
    {
    	
    	if (upc == null)
    	{
    		throw new DAOException("The UPC value is not valid");
    	}
    	
    	PreparedStatement ps = null;
    	
    	try{
    		
    		ps = connection.prepareStatement(upcSQL);
			ps.setString(1, upc);  
			ResultSet resultSet = ps.executeQuery();
			
			if (!resultSet.next())
			{
				return null;
			}
			
			Product product = new Product();
			product.setId(resultSet.getLong("id"));
			product.setProdName(resultSet.getString("prod_name"));
			product.setProdDescription(resultSet.getString("prod_desc"));
			product.setProdCategory(resultSet.getInt("prod_category"));
			product.setProdUPC(resultSet.getString("prod_upc"));
			return product;

		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
    }

}
