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

package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	@Override
    public Product create(Product product) throws SQLException, DAOException
    {
        ProductDAO productDAO = new ProductDaoImpl(); 
        Connection connection = dataSource.getConnection();
        
        try {
            connection.setAutoCommit(false);
            Product prod = productDAO.create(connection, product);
            connection.commit();
            return prod;
        }
        catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Product retrieve(Long id) throws SQLException, DAOException
    {
    	 ProductDAO productDAO = new ProductDaoImpl(); 
         Connection connection = dataSource.getConnection();
         
         try {
             connection.setAutoCommit(false);
             Product prod = productDAO.retrieve(connection, id);
             connection.commit();
             return prod;
         }
         catch (Exception ex) {
             connection.rollback();
             throw ex;
         }
         finally {
             if (connection != null) {
                 connection.setAutoCommit(true);
             }
             if (connection != null && !connection.isClosed()) {
                 connection.close();
             }
         }
         
    }

    @Override
    public int update(Product product) throws SQLException, DAOException
    {
    	ProductDAO productDAO = new ProductDaoImpl(); 
        Connection connection = dataSource.getConnection();
        
        try {
            connection.setAutoCommit(false);
            int ID = productDAO.update(connection, product);
            connection.commit();
            return ID;
        }
        catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public int delete(Long id) throws SQLException, DAOException
    {
    	ProductDAO productDAO = new ProductDaoImpl(); 
        Connection connection = dataSource.getConnection();
        
        try {
            connection.setAutoCommit(false);
            int ID = productDAO.delete(connection, id);
            connection.commit();
            return ID;
        }
        catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Product retrieveByUPC(String upc) throws SQLException, DAOException
    {
    	ProductDAO productDAO = new ProductDaoImpl(); 
        Connection connection = dataSource.getConnection();
        
        try {
            connection.setAutoCommit(false);
            Product prod = productDAO.retrieveByUPC(connection, upc);
            connection.commit();
            return prod;
        }
        catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
    {
    	ProductDAO productDAO = new ProductDaoImpl(); 
        Connection connection = dataSource.getConnection();
        
        try {
            connection.setAutoCommit(false);
            
            List<Product> finalList = new ArrayList<Product>(); 
            finalList = productDAO.retrieveByCategory(connection, category);
            connection.commit();
            return finalList;
        }
        catch (Exception ex) {
            connection.rollback();
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

}
